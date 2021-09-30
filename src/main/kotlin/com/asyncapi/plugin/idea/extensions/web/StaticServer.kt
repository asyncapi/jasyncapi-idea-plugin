package com.asyncapi.plugin.idea.extensions.web

import com.asyncapi.plugin.idea._core.SchemaHtmlRenderer
import com.intellij.ide.browsers.OpenInBrowserRequest
import com.intellij.json.JsonFileType
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Url
import com.intellij.util.Urls
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.*
import org.jetbrains.ide.BuiltInServerManager
import org.jetbrains.ide.HttpRequestHandler
import org.jetbrains.io.send
import org.jetbrains.yaml.YAMLFileType
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

class StaticServer : HttpRequestHandler() {

    private val schemaHtmlRenderer = SchemaHtmlRenderer()

    fun getUrl(request: OpenInBrowserRequest, file: VirtualFile): Url? {
        val port = BuiltInServerManager.getInstance().port
        val schemaUrl = URLEncoder.encode(file.path, StandardCharsets.UTF_8.toString())
        val projectUrl = URLEncoder.encode(request.project.presentableUrl, StandardCharsets.UTF_8.toString())
        val projectName = URLEncoder.encode(request.project.name, StandardCharsets.UTF_8.toString())
        val address = "http://localhost:$port/asyncapi/render" +
                "?schemaUrl=$schemaUrl" +
                "&projectUrl=$projectUrl" +
                "&projectName=$projectName" +
                "&_ij_reload=RELOAD_ON_SAVE"

        val url = Urls.parseEncoded(address)
//        val url = Urls.parseEncoded(getStaticUrl())
        return if (request.isAppendAccessToken) {
            BuiltInServerManager.getInstance().addAuthToken(Objects.requireNonNull<Url>(url))
        } else {
            url
        }
    }

    override fun isAccessible(request: HttpRequest): Boolean {
        return request.uri().startsWith("/asyncapi") && super.isAccessible(request)
    }

    override fun isSupported(request: FullHttpRequest): Boolean {
        return request.uri().startsWith("/asyncapi") && super.isAccessible(request)
    }

    override fun process(
        urlDecoder: QueryStringDecoder,
        request: FullHttpRequest,
        context: ChannelHandlerContext
    ): Boolean {
        val htmlPage = urlDecoder.path().startsWith("/asyncapi/render")
        val reference = urlDecoder.path().startsWith("/asyncapi/resources") && urlDecoder.parameters().contains("referenceUrl")
        val schema = urlDecoder.path().startsWith("/asyncapi/resources") && urlDecoder.parameters().contains("schemaUrl")

        return if (htmlPage) {
            val schemaUrl = if (urlDecoder.parameters().contains("schemaUrl")) {
                urlDecoder.parameters()["schemaUrl"]?.get(0)
            } else {
                null
            }

            val html = schemaHtmlRenderer.render(schemaUrl).toByteArray(StandardCharsets.UTF_8)

            sendResponse(html, request, context, "text/html")
            true
        } else if (reference) {
            val referenceUrl = if (urlDecoder.parameters().contains("referenceUrl")) {
                urlDecoder.parameters()["referenceUrl"]?.get(0)
            } else {
                null
            }

            referenceUrl ?: return false
            val requestedFile = resolveResource(referenceUrl)
            requestedFile ?: return false

            sendResponse(requestedFile.second, request, context, requestedFile.first)
            true
        } else if (schema) {
            val schemaUrl = if (urlDecoder.parameters().contains("schemaUrl")) {
                urlDecoder.parameters()["schemaUrl"]?.get(0)
            } else {
                null
            }

            schemaUrl ?: return false
            val requestedFile = resolveResource(schemaUrl)
            requestedFile ?: return false

            sendResponse(requestedFile.second, request, context, requestedFile.first)
            true
        } else {
            false
        }
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

    private fun resolveResource(resourceUrl: String): Pair<String, ByteArray>? {
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

        return Pair(contentType, requestedFile.readBytes())
    }

}