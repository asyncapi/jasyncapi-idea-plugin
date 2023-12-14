package com.asyncapi.plugin.idea._core

import com.asyncapi.plugin.idea._core.render.WebSocketRendererProvider
import com.asyncapi.plugin.idea.extensions.web.UrlProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.intellij.json.JsonFileType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.io.systemIndependentPath
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import io.netty.handler.codec.http.FullHttpRequest
import org.jetbrains.yaml.YAMLFileType
import java.io.File
import java.util.function.Supplier

/**
 * @author Pavel Bodiachevskii
 * @since 1.1.0
 */
@Service
class AsyncAPISpecificationHtmlRenderer {

    private val webSocketRendererProvider = service<WebSocketRendererProvider>()

    private val urlProvider = service<UrlProvider>()
    private val specificationTemplateUrl = "/ui/index.html"
    private val specificationTemplateCssUrl = "default(1.2.9).min.css"
    private val specificationTemplateJsUrl = "index(1.2.9).js"

    fun render(request: FullHttpRequest, specificationUrl: String?): String {
        specificationUrl ?: return "specification: not found."

        val specificationFile = File(specificationUrl)
        if (!specificationFile.exists()) {
            return "specification: $specificationUrl not found."
        }

        val specificationVirtualFile = LocalFileSystem.getInstance().findFileByIoFile(specificationFile)
        specificationVirtualFile ?: return "specification: $specificationUrl not found."
        if (specificationVirtualFile.fileType !is YAMLFileType && specificationVirtualFile.fileType !is JsonFileType) {
            return "specification: $specificationUrl not in json or yaml format."
        }

        val isJson = specificationVirtualFile.fileType is JsonFileType
        val specification = replaceLocalReferences(specificationFile.readText(Charsets.UTF_8), specificationVirtualFile, isJson)
        val temporalSpecificationUrl = saveAsTemporalFile(specification, isJson)

        val specificationTemplate = this.javaClass.getResource(specificationTemplateUrl)
        specificationTemplate ?: return "specification template not found."

        val webSocket = webSocketRendererProvider.provide(
            fullHttpRequest = request,
            supplier = Supplier<VirtualFile?> { specificationVirtualFile }
        )

        return specificationTemplate.readText(Charsets.UTF_8)
            .replace(
                "url: '',",
                "url: '${urlProvider.specification(temporalSpecificationUrl)}',"
            )
            .replace(
                "<link rel=\"stylesheet\" href=\"\">",
                "<link rel=\"stylesheet\" href=\"${urlProvider.resource(specificationTemplateCssUrl)}\">"
            ).replace(
                "<script src=\"\"></script>",
                "<script src=\"${urlProvider.resource(specificationTemplateJsUrl)}\"></script>"
            ).replace(
                "<!-- WebSocket -->",
                webSocket?.toString() ?: ""
            )
    }

    private fun replaceLocalReferences(specification: String, specificationFile: VirtualFile, isJson: Boolean): String {
        val objectMapper = if (isJson) {
            ObjectMapper()
        } else {
            ObjectMapper(YAMLFactory())
        }

        val tree = objectMapper.readTree(specification)
        tree.findParents("\$ref").forEach {
            val referenceValue = (it as ObjectNode).get("\$ref")
            if (referenceValue.toPrettyString().startsWith("\"./") || referenceValue.toPrettyString().startsWith("\"../")) {
                (it).put("\$ref", localReferenceToFileUrl(referenceValue.toPrettyString(), specificationFile))
            }
        }

        return objectMapper.writeValueAsString(tree)
    }

    private fun localReferenceToFileUrl(localReference: String, specificationFile: VirtualFile): String {
        val rawFileReference = localReference.removePrefix("\"").removeSuffix("\"")
        val fileReference = rawFileReference.split("#/").getOrNull(0)
        val specificationComponentReference = rawFileReference.split("#/").getOrNull(1)
        fileReference ?: return rawFileReference

        val referencedFile = specificationFile.parent.findFileByRelativePath(fileReference)
        referencedFile ?: return fileReference

        return urlProvider.reference(referencedFile.path, specificationComponentReference)
    }

    private fun saveAsTemporalFile(specification: String, isJson: Boolean): String {
        val suffix = if (isJson) {
            ".json"
        } else {
            ".yaml"
        }

        val tempSpecification = FileUtil.createTempFile("jasyncapi-idea-plugin-${System.currentTimeMillis()}", suffix, true)
        tempSpecification.writeText(specification, Charsets.UTF_8)

        return tempSpecification.systemIndependentPath
    }

}