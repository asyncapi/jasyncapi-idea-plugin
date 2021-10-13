package com.asyncapi.plugin.idea._core

import com.intellij.json.JsonFileType
import com.intellij.openapi.vfs.LocalFileSystem
import org.jetbrains.yaml.YAMLFileType
import java.io.File

class SchemaHtmlRenderer {

    private val schemaTemplateUrl = "/ui/index.html"

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
        val schema = SchemaHelper.replaceLocalReferences(schemaFile.readText(Charsets.UTF_8), schemaVirtualFile, isJson)
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
                "url: '${SchemaHelper.urlToRequestedSchema(temporalSchemaUrl)}',"
            )
    }

}