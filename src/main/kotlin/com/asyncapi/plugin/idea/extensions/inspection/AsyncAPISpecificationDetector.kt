package com.asyncapi.plugin.idea.extensions.inspection

import com.asyncapi.plugin.idea.extensions.index.v2.AsyncAPISpecificationIndex
import com.intellij.json.psi.JsonFile
import com.intellij.psi.PsiFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.FileBasedIndex
import org.jetbrains.yaml.psi.YAMLFile

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPISpecificationDetector {

    fun isAsyncAPIJsonSpecification(psiFile: PsiFile?): Boolean {
        psiFile ?: return false
        if (psiFile !is JsonFile) {
            return false
        }

        return indexedAsyncAPISpecifications(psiFile).contains(psiFile.virtualFile?.path)
    }

    fun isAsyncAPIYamlSpecification(psiFile: PsiFile?): Boolean {
        psiFile ?: return false
        if (psiFile !is YAMLFile) {
            return false
        }

        return indexedAsyncAPISpecifications(psiFile).contains(psiFile.virtualFile?.path)
    }

    fun isAsyncAPISpecification(psiFile: PsiFile?): Boolean {
        psiFile ?: return false
        return when (psiFile) {
            is JsonFile -> return isAsyncAPIJsonSpecification(psiFile)
            is YAMLFile -> return isAsyncAPIYamlSpecification(psiFile)
            else -> false
        }
    }

    private fun isAsyncAPISpecificationJsonComponent(psiFile: PsiFile?): Boolean {
        psiFile ?: return false
        if (psiFile !is JsonFile) {
            return false
        }

        return indexedAsyncAPISpecificationReferences(psiFile).contains(psiFile.virtualFile?.path)
    }

    private fun isAsyncAPISpecificationYamlComponent(psiFile: PsiFile?): Boolean {
        psiFile ?: return false
        if (psiFile !is YAMLFile) {
            return false
        }

        return indexedAsyncAPISpecificationReferences(psiFile).contains(psiFile.virtualFile?.path)
    }

    fun isAsyncAPISpecificationComponent(psiFile: PsiFile?): Boolean {
        psiFile ?: return false
        return when (psiFile) {
            is JsonFile -> return isAsyncAPISpecificationJsonComponent(psiFile)
            is YAMLFile -> return isAsyncAPISpecificationYamlComponent(psiFile)
            else -> false
        }
    }

    private fun indexedAsyncAPISpecifications(asyncapiSpecification: PsiFile): List<String> {
        return FileBasedIndex.getInstance().getValues(
                AsyncAPISpecificationIndex.asyncapiIndexId,
                AsyncAPISpecificationIndex.asyncapi,
                GlobalSearchScope.allScope(asyncapiSpecification.project)
        ).flatten()
    }

    private fun indexedAsyncAPISpecificationReferences(asyncapiSpecification: PsiFile): List<String> {
        return FileBasedIndex.getInstance().getValues(
                AsyncAPISpecificationIndex.asyncapiIndexId,
                AsyncAPISpecificationIndex.references,
                GlobalSearchScope.allScope(asyncapiSpecification.project)
        ).flatten()
    }

}