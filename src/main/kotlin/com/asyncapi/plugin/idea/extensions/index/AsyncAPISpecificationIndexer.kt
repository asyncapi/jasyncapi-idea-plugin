package com.asyncapi.plugin.idea.extensions.index

import com.asyncapi.plugin.idea._core.AsyncAPISpecificationRecognizer
import com.intellij.json.psi.JsonFile
import com.intellij.openapi.components.service
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import org.jetbrains.yaml.psi.YAMLFile

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPISpecificationIndexer: DataIndexer<String, Set<String>, FileContent> {

    private val asyncAPISpecificationRecognizer = service<AsyncAPISpecificationRecognizer>()

    override fun map(inputData: FileContent): MutableMap<String, Set<String>> {
        val index = mutableMapOf<String, Set<String>>()

        if (!asyncAPISpecificationRecognizer.isSpecification(inputData.psiFile)) {
            return index
        }

        val asyncapiSpecification = when(inputData.psiFile) {
            is JsonFile -> inputData.psiFile as JsonFile
            is YAMLFile -> inputData.psiFile as YAMLFile
            else -> null
        }
        asyncapiSpecification ?: return index

        index[AsyncAPISpecificationIndex.asyncapi] = setOf(inputData.file.path)
        var foundReferences = emptySet<String>()
        AsyncAPISpecificationReferencesCollector(asyncapiSpecification, inputData.file.parent).collectFiles().forEach { (referenceType, references) ->
            index[referenceType] = references

            foundReferences = foundReferences.plus(references)
            index[AsyncAPISpecificationIndex.references] = foundReferences
        }

        return index
    }

}