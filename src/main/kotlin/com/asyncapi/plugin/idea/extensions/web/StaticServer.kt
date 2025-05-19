package com.asyncapi.plugin.idea.extensions.web

import com.asyncapi.plugin.idea._core.AsyncAPISpecificationHtmlRenderer
import com.intellij.json.JsonFileType
import com.intellij.openapi.components.service
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.util.io.getHostName
import com.intellij.util.io.isLocalHost
import com.intellij.util.io.origin
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.*
import org.jetbrains.ide.HttpRequestHandler
import org.jetbrains.io.send
import org.jetbrains.yaml.YAMLFileType
import java.io.File
import java.nio.charset.StandardCharsets
import kotlin.text.toByteArray

/**
 * @author Pavel Bodiachevskii
 * @since 1.1.0
 */
class StaticServer : HttpRequestHandler() {

    private val urlProvider = service<UrlProvider>()
    private val asyncAPISpecificationHtmlRenderer = service<AsyncAPISpecificationHtmlRenderer>()

    override fun isAccessible(request: HttpRequest): Boolean {
        // TODO: research why netty recognize origin: null as "null" instead of null
        if (request.origin.equals("null") && urlProvider.isPlugin(request)) {
            val hostName = getHostName(request)
//            return hostName != null && isOriginAllowed(request) != OriginCheckResult.FORBID && isLocalHost(hostName)
            return hostName != null && isLocalHost(hostName)
        }

        return urlProvider.isPlugin(request) && super.isAccessible(request)
    }

    override fun isSupported(request: FullHttpRequest): Boolean {
        return urlProvider.isPlugin(request) && super.isSupported(request)
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
                            asyncAPISpecificationHtmlRenderer.render(request, resourceUrl).toByteArray(StandardCharsets.UTF_8)
                        )
                    }
                }
            }
            UrlProvider.UrlType.SPECIFICATION_FILE, UrlProvider.UrlType.REFERENCED_SPECIFICATION_COMPONENT_FILE -> {
                object : ResourceHandler {
                    override fun handle(resourceUrl: String): Resource? {
                        return resolveSpecificationComponent(resourceUrl)
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
            UrlProvider.UrlType.HTML_FILE, UrlProvider.UrlType.SPECIFICATION_FILE -> UrlProvider.SPECIFICATION_PARAMETER_NAME
            UrlProvider.UrlType.REFERENCED_SPECIFICATION_COMPONENT_FILE -> UrlProvider.REFERENCED_SPECIFICATION_COMPONENT_PARAMETER_NAME
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

    private fun resolveSpecificationComponent(specificationComponentUrl: String): Resource? {
        val requestedFile = File(specificationComponentUrl)
        if (!requestedFile.exists()) {
            return null
        }

        val referenceVirtualFile = LocalFileSystem.getInstance().findFileByIoFile(requestedFile)
        referenceVirtualFile ?: return null

        if ("avsc" == referenceVirtualFile.extension) {
            return Resource("application/avro", requestedFile.readBytes())
        }

        if ("xsd" == referenceVirtualFile.extension) {
            return Resource("application/xml", requestedFile.readBytes())
        }

        if (referenceVirtualFile.fileType !is YAMLFileType && referenceVirtualFile.fileType !is JsonFileType) {
            return null
        }

        val isJson = referenceVirtualFile.fileType is JsonFileType
        val contentType = if (isJson) {
            "application/json"
        } else {
            "application/x-yaml"
        }

        val componentJson = asyncAPISpecificationHtmlRenderer.replaceLocalReferences(
            requestedFile.readText(StandardCharsets.UTF_8),
            referenceVirtualFile,
            isJson
        )
        return Resource(contentType, componentJson.toByteArray(StandardCharsets.UTF_8))
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