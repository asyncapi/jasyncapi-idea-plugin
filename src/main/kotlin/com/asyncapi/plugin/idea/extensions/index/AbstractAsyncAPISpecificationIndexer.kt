package com.asyncapi.plugin.idea.extensions.index

import com.asyncapi.plugin.idea._core.AsyncAPISpecificationRecognizer
import com.intellij.json.psi.JsonFile
import com.intellij.openapi.components.service
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.util.indexing.DataIndexer
import com.intellij.util.indexing.FileContent
import org.jetbrains.yaml.psi.YAMLFile

/**
 * @author Pavel Bodiachevskii
 */
abstract class AbstractAsyncAPISpecificationIndexer: DataIndexer<String, Set<String>, FileContent> {

    private val asyncAPISpecificationRecognizer = service<AsyncAPISpecificationRecognizer>()

    abstract fun asyncapiIndexKey(): String

    abstract fun referencesIndexKey(): String

    abstract fun asyncAPISpecificationReferencesCollector(
        asyncAPISpecification: PsiFile,
        asyncAPISpecificationDir: VirtualFile
    ): AbstractAsyncAPISpecificationReferencesCollector

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

        index[asyncapiIndexKey()] = setOf(inputData.file.path)
        var foundReferences = emptySet<String>()
        asyncAPISpecificationReferencesCollector(asyncapiSpecification, inputData.file.parent).collectFiles().forEach { (referenceType, references) ->
            index[referenceType] = references

            foundReferences = foundReferences.plus(references)
            index[referencesIndexKey()] = foundReferences
        }

        return index
    }

}