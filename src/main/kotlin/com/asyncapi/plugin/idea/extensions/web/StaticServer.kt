package com.asyncapi.plugin.idea.extensions.web

import com.asyncapi.plugin.idea._core.AsyncAPISchemaHtmlRenderer
import com.intellij.json.JsonFileType
import com.intellij.openapi.components.service
import com.intellij.openapi.vfs.LocalFileSystem
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.*
import org.jetbrains.ide.HttpRequestHandler
import org.jetbrains.io.send
import org.jetbrains.yaml.YAMLFileType
import java.io.File
import java.nio.charset.StandardCharsets

class StaticServer : HttpRequestHandler() {

    private val urlProvider = service<UrlProvider>()
    private val asyncAPISchemaHtmlRenderer = service<AsyncAPISchemaHtmlRenderer>()

    override fun isAccessible(request: HttpRequest): Boolean {
        return urlProvider.isPlugin(request) && super.isAccessible(request)
    }

    override fun isSupported(request: FullHttpRequest): Boolean {
        return urlProvider.isPlugin(request) && super.isAccessible(request)
    }

    override fun process(
        urlDecoder: QueryStringDecoder,
        request: FullHttpRequest,
        context: ChannelHandlerContext
    ): Boolean {
        val urlType = urlProvider.recognize(urlDecoder)
        urlType ?: return false

        val resourceHandler = when (urlType) {
            UrlProvider.UrlType.HTML_FILE -> {
                object : ResourceHandler {
                    override fun handle(resourceUrl: String): Resource {
                        return Resource(
                            "text/html",
                            asyncAPISchemaHtmlRenderer.render(resourceUrl).toByteArray(StandardCharsets.UTF_8)
                        )
                    }
                }
            }
            UrlProvider.UrlType.SCHEMA_FILE, UrlProvider.UrlType.REFERENCED_SCHEMA_FILE -> {
                object : ResourceHandler {
                    override fun handle(resourceUrl: String): Resource? {
                        return resolveSchemaResource(resourceUrl)
                    }
                }
            }
            UrlProvider.UrlType.RESOURCE_FILE -> {
                object : ResourceHandler {
                    override fun handle(resourceUrl: String): Resource? {
                        return resolveResource(resourceUrl)
                    }
                }
            }
        }

        val resourceParameterName = when (urlType) {
            UrlProvider.UrlType.HTML_FILE, UrlProvider.UrlType.SCHEMA_FILE -> UrlProvider.SCHEMA_PARAMETER_NAME
            UrlProvider.UrlType.REFERENCED_SCHEMA_FILE -> UrlProvider.REFERENCED_SCHEMA_PARAMETER_NAME
            UrlProvider.UrlType.RESOURCE_FILE -> UrlProvider.RESOURCE_PARAMETER_NAME
        }

        val resource = handleRequest(urlDecoder, resourceParameterName, resourceHandler)
        resource?: return false

        sendResponse(resource.content, request, context, resource.contentType)
        return true
    }

    private fun handleRequest(urlDecoder: QueryStringDecoder,
                              resource: String,
                              resourceHandler: ResourceHandler
    ): Resource? {
        val resourceUrl = urlProvider.requestParameter(urlDecoder, resource)
        resourceUrl ?: return null

        return resourceHandler.handle(resourceUrl)
    }

    private fun sendResponse(content: ByteArray,
                             request: FullHttpRequest,
                             context: ChannelHandlerContext,
                             contentType: String
    ) {
        val response: FullHttpResponse = DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1,
            HttpResponseStatus.OK,
            Unpooled.wrappedBuffer(content)
        )
        response.headers()[HttpHeaderNames.CONTENT_TYPE] = "$contentType; charset=UTF-8"
        response.headers()[HttpHeaderNames.CACHE_CONTROL] = "max-age=5, private, must-revalidate"
        response.headers()["Referrer-Policy"] = "no-referrer"
        response.send(context.channel(), request)
    }

    private fun resolveSchemaResource(resourceUrl: String): Resource? {
        val requestedFile = File(resourceUrl)
        if (!requestedFile.exists()) {
            return null
        }

        val referenceVirtualFile = LocalFileSystem.getInstance().findFileByIoFile(requestedFile)
        referenceVirtualFile ?: return null

        if (referenceVirtualFile.fileType !is YAMLFileType && referenceVirtualFile.fileType !is JsonFileType) {
            return null
        }

        val isJson = referenceVirtualFile.fileType is JsonFileType
        val contentType = if (isJson) {
            "application/json"
        } else {
            "application/x-yaml"
        }

        return Resource(contentType, requestedFile.readBytes())
    }

    private fun resolveResource(resourceName: String): Resource? {
        val resource = try {
            this.javaClass.getResource("/ui/$resourceName")
        } catch (e: Exception) {
            null
        }
        resource ?: return null

        val contentType = if (resourceName.endsWith(".css")) {
            "text/css; charset=utf-8"
        } else if (resourceName.endsWith(".js")) {
            "application/javascript; charset=utf-8"
        } else {
            null
        }

        contentType ?: return null
        return Resource(contentType, resource.readBytes())
    }

    private class Resource(
        val contentType: String,
        val content: ByteArray
    )

    private interface ResourceHandler {

        fun handle(resourceUrl: String): Resource?

    }

}