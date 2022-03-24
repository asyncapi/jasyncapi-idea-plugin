package com.asyncapi.plugin.idea.extensions.index

import com.asyncapi.plugin.idea._core.AsyncAPISchemaRecognizer
import com.asyncapi.plugin.idea._core.AsyncAPISchemaReferencesCollector
import com.intellij.json.psi.JsonFile
import com.intellij.openapi.components.service
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import org.jetbrains.yaml.psi.YAMLFile

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPISchemaIndexer: DataIndexer<String, Set<String>, FileContent> {

    private val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

    override fun map(inputData: FileContent): MutableMap<String, Set<String>> {
        val index = mutableMapOf<String, Set<String>>()

        if (!asyncAPISchemaRecognizer.isSchema(inputData.psiFile)) {
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

}