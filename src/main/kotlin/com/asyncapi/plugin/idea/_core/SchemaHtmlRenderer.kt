package com.asyncapi.plugin.idea._core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.intellij.json.JsonFileType
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Urls
import org.jetbrains.ide.BuiltInServerManager
import org.jetbrains.yaml.YAMLFileType
import java.io.File

class SchemaHtmlRenderer {

    private val schemaTemplateUrl = "/ui/index.html"
    private val serverManager = BuiltInServerManager.getInstance()

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
        val temporalSchemaUrl = SchemaHelper.saveAsTemporalFile(schema, isJson)

        val schemaTemplate = this.javaClass.getResource(schemaTemplateUrl)
        schemaTemplate ?: return "schema template not found."

        return schemaTemplate.readText(Charsets.UTF_8)
//            .replace(
//                "schema: ``,",
//                "schema: `${schema.removePrefix("\"").removeSuffix("\"")}`,"
//            )
            .replace(
                "url: '',",
                "url: '${urlToRequestedSchema(temporalSchemaUrl)}',"
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

        return urlToReferencedFile(referencedFile.path, schemaReference)
    }

    private fun urlToReferencedFile(fileUrl: String, schemaReference: String?): String {
        val url = if (schemaReference != null) {
            Urls.parseEncoded("http://localhost:${serverManager.port}/asyncapi/resources?referenceUrl=$fileUrl#/$schemaReference")
        } else {
            Urls.parseEncoded("http://localhost:${serverManager.port}/asyncapi/resources?referenceUrl=$fileUrl")
        }

        return serverManager.addAuthToken(url!!).toExternalForm()
    }

    private fun urlToRequestedSchema(schemaUrl: String): String {
        val url = Urls.parseEncoded("http://localhost:${serverManager.port}/asyncapi/resources?schemaUrl=$schemaUrl")

        return serverManager.addAuthToken(url!!).toExternalForm()
    }

}