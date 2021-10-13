package com.asyncapi.plugin.idea._core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Urls
import org.jetbrains.ide.BuiltInServerManager

object SchemaHelper {

    private val serverManager = BuiltInServerManager.getInstance()

    fun saveAsTemporalFile(schema: String, isJson: Boolean): String {
        val suffix = if (isJson) {
            ".json"
        } else {
            ".yaml"
        }

        val tempSchema = FileUtil.createTempFile("jasyncapi-idea-plugin-${System.currentTimeMillis()}", suffix, true)
        tempSchema.writeText(schema, Charsets.UTF_8)

        return tempSchema.path
    }

    fun replaceLocalReferences(schema: String, schemaFile: VirtualFile, isJson: Boolean): String {
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

    fun urlToRequestedSchema(schemaUrl: String): String {
        val url = Urls.parseEncoded("http://localhost:${serverManager.port}/asyncapi/resources?schemaUrl=$schemaUrl")

        return serverManager.addAuthToken(url!!).toExternalForm()
    }

}