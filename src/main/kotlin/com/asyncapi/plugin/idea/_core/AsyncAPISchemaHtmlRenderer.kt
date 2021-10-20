package com.asyncapi.plugin.idea._core

import com.asyncapi.plugin.idea.extensions.web.UrlProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.intellij.json.JsonFileType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.yaml.YAMLFileType
import java.io.File

/**
 * @author Pavel Bodiachevskii
 * @since 1.1.0
 */
@Service
class AsyncAPISchemaHtmlRenderer {

    private val urlProvider = service<UrlProvider>()
    private val schemaTemplateUrl = "/ui/index.html"
    private val schemaTemplateCssUrl = "default(1.0.0-next.12).min.css"
    private val schemaTemplateJsUrl = "index(1.0.0-next.12).js"

    fun render(schemaUrl: String?): String {
        schemaUrl ?: return "schema: not found."

        val schemaFile = File(schemaUrl)
        if (!schemaFile.exists()) {
            return "schema: $schemaUrl not found."
        }

        val schemaVirtualFile = LocalFileSystem.getInstance().findFileByIoFile(schemaFile)
        schemaVirtualFile ?: return "schema: $schemaUrl not found."
        if (schemaVirtualFile.fileType !is YAMLFileType && schemaVirtualFile.fileType !is JsonFileType) {
            return "schema: $schemaUrl not in json or yaml format."
        }

        val isJson = schemaVirtualFile.fileType is JsonFileType
        val schema = replaceLocalReferences(schemaFile.readText(Charsets.UTF_8), schemaVirtualFile, isJson)
        val temporalSchemaUrl = saveAsTemporalFile(schema, isJson)

        val schemaTemplate = this.javaClass.getResource(schemaTemplateUrl)
        schemaTemplate ?: return "schema template not found."

        return schemaTemplate.readText(Charsets.UTF_8)
            .replace(
                "url: '',",
                "url: '${urlProvider.schema(temporalSchemaUrl)}',"
            )
            .replace(
                "<link rel=\"stylesheet\" href=\"\">",
                "<link rel=\"stylesheet\" href=\"${urlProvider.resource(schemaTemplateCssUrl)}\">"
            ).replace(
                "<script src=\"\"></script>",
                "<script src=\"${urlProvider.resource(schemaTemplateJsUrl)}\"></script>"
            )
    }

    private fun replaceLocalReferences(schema: String, schemaFile: VirtualFile, isJson: Boolean): String {
        val objectMapper = if (isJson) {
            ObjectMapper()
        } else {
            ObjectMapper(YAMLFactory())
        }

        val tree = objectMapper.readTree(schema)
        tree.findParents("\$ref").forEach {
            val referenceValue = (it as ObjectNode).get("\$ref")
            if (referenceValue.toPrettyString().startsWith("\"./") || referenceValue.toPrettyString().startsWith("\"../")) {
                (it).put("\$ref", localReferenceToFileUrl(referenceValue.toPrettyString(), schemaFile))
            }
        }

        return objectMapper.writeValueAsString(tree)
    }

    private fun localReferenceToFileUrl(localReference: String, schemaFile: VirtualFile): String {
        val rawFileReference = localReference.removePrefix("\"").removeSuffix("\"")
        val fileReference = rawFileReference.split("#/").getOrNull(0)
        val schemaReference = rawFileReference.split("#/").getOrNull(1)
        fileReference ?: return rawFileReference

        val referencedFile = schemaFile.parent.findFileByRelativePath(fileReference)
        referencedFile ?: return fileReference

        return urlProvider.reference(referencedFile.path, schemaReference)
    }

    private fun saveAsTemporalFile(schema: String, isJson: Boolean): String {
        val suffix = if (isJson) {
            ".json"
        } else {
            ".yaml"
        }

        val tempSchema = FileUtil.createTempFile("jasyncapi-idea-plugin-${System.currentTimeMillis()}", suffix, true)
        tempSchema.writeText(schema, Charsets.UTF_8)

        return tempSchema.path
    }

}