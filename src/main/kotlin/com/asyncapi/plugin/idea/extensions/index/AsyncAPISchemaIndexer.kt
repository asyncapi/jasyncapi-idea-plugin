package com.asyncapi.plugin.idea.extensions.index

import com.asyncapi.plugin.idea._core.AsyncAPISchemaReferencesCollector
import com.asyncapi.plugin.idea._core.xpath.JsonFileXPath
import com.asyncapi.plugin.idea._core.xpath.YamlFileXPath
import com.intellij.json.psi.JsonFile
import com.intellij.psi.PsiFile
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import org.jetbrains.yaml.psi.YAMLFile

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPISchemaIndexer: DataIndexer<String, Set<String>, FileContent> {

    override fun map(inputData: FileContent): MutableMap<String, Set<String>> {
        val index = mutableMapOf<String, Set<String>>()

        if (!isAsyncAPISchema(inputData.psiFile)) {
            return index
        }

        val asyncapiSchema = when(inputData.psiFile) {
            is JsonFile -> inputData.psiFile as JsonFile
            is YAMLFile -> inputData.psiFile as YAMLFile
            else -> null
        }
        asyncapiSchema ?: return index

        index[AsyncAPISchemaIndex.asyncapi] = setOf(inputData.file.path)
        var foundReferences = emptySet<String>()
        AsyncAPISchemaReferencesCollector(asyncapiSchema, inputData.file.parent).collectFiles().forEach { (referenceType, references) ->
            index[referenceType] = references

            foundReferences = foundReferences.plus(references)
            index[AsyncAPISchemaIndex.references] = foundReferences
        }

        return index
    }

    private fun isAsyncAPISchema(inputData: PsiFile): Boolean {
        val psiXPath = "$.asyncapi"
        val asyncapi: String? = when (inputData) {
            is JsonFile -> JsonFileXPath.findText(inputData as? JsonFile, psiXPath).firstOrNull()
            is YAMLFile -> YamlFileXPath.findText(inputData as? YAMLFile, psiXPath).firstOrNull()
            else -> ""
        }

        return asyncapi == "2.0.0"
    }

}