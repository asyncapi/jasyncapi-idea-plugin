package com.asyncapi.plugin.idea.extensions.web

import com.intellij.ide.browsers.OpenInBrowserRequest
import com.intellij.openapi.components.Service
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Url
import com.intellij.util.Urls
import io.netty.handler.codec.http.FullHttpRequest
import io.netty.handler.codec.http.HttpRequest
import io.netty.handler.codec.http.QueryStringDecoder
import org.jetbrains.ide.BuiltInServerManager
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class UrlProvider {

    private val staticServerManager = BuiltInServerManager.getInstance()

    private val staticServerSchema = "http"
    private val staticServerHost = "localhost"
    private val staticServerPort = staticServerManager.port
    private val staticServerUrl = "$staticServerSchema://$staticServerHost:$staticServerPort"

    private val pluginSpace = "asyncapi"
    private val renderRequest = "$pluginSpace/render"
    private val resourcesRequest = "$pluginSpace/resources"

    fun isPlugin(request: HttpRequest) = request.uri().startsWith("/$pluginSpace")

    fun isPlugin(request: FullHttpRequest) = request.uri().startsWith("/$pluginSpace")

    fun render(request: OpenInBrowserRequest, file: VirtualFile): Url? {
        val url = "$staticServerUrl/$renderRequest?${renderParams(request, file)}"

        val encodedUrl = Urls.parseEncoded(url)
        return if (request.isAppendAccessToken) {
            BuiltInServerManager.getInstance().addAuthToken(Objects.requireNonNull<Url>(encodedUrl))
        } else {
            encodedUrl
        }
    }

    private fun renderParams(request: OpenInBrowserRequest, file: VirtualFile): String {
        val schemaUrl = URLEncoder.encode(file.path, StandardCharsets.UTF_8.toString())
        val projectUrl = URLEncoder.encode(request.project.presentableUrl, StandardCharsets.UTF_8.toString())
        val projectName = URLEncoder.encode(request.project.name, StandardCharsets.UTF_8.toString())

        return "$SCHEMA_PARAMETER_NAME=$schemaUrl" +
                "&projectUrl=$projectUrl" +
                "&projectName=$projectName" +
                "&_ij_reload=RELOAD_ON_SAVE"
    }

    fun reference(fileUrl: String, schemaReference: String?): String {
        val url = Urls.parseEncoded("$staticServerUrl/$resourcesRequest?${referenceParams(fileUrl, schemaReference)}")

        return staticServerManager.addAuthToken(url!!).toExternalForm()
    }

    private fun referenceParams(fileUrl: String, schemaReference: String?): String {
        return if (schemaReference != null) {
            "$REFERENCED_SCHEMA_PARAMETER_NAME=$fileUrl#/$schemaReference"
        } else {
            "$REFERENCED_SCHEMA_PARAMETER_NAME=$fileUrl"
        }
    }

    fun schema(schemaUrl: String): String {
        val url = Urls.parseEncoded("$staticServerUrl/$resourcesRequest?$SCHEMA_PARAMETER_NAME=$schemaUrl")

        return staticServerManager.addAuthToken(url!!).toExternalForm()
    }

    fun recognize(urlDecoder: QueryStringDecoder): UrlType? {
        if (urlDecoder.path().startsWith("/$renderRequest")) {
            return UrlType.HTML_FILE
        }

        if (urlDecoder.path().startsWith("/$resourcesRequest")) {
            var urlType: UrlType? = null

            if (urlDecoder.parameters().contains(REFERENCED_SCHEMA_PARAMETER_NAME)) {
                urlType = UrlType.REFERENCED_SCHEMA_FILE
            } else if (urlDecoder.parameters().contains(SCHEMA_PARAMETER_NAME)) {
                urlType = UrlType.SCHEMA_FILE
            }

            return urlType
        }

        return null
    }

    fun requestParameter(urlDecoder: QueryStringDecoder, parameterName: String): String? {
        return if (urlDecoder.parameters().contains(parameterName)) {
            urlDecoder.parameters()[parameterName]?.get(0)
        } else {
            null
        }
    }

    enum class UrlType {
        HTML_FILE,
        SCHEMA_FILE,
        REFERENCED_SCHEMA_FILE,
    }

    companion object {

        const val SCHEMA_PARAMETER_NAME = "schemaUrl"
        const val REFERENCED_SCHEMA_PARAMETER_NAME = "referenceUrl"

    }

}