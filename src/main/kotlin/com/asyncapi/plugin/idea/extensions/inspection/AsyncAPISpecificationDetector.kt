package com.asyncapi.plugin.idea.extensions.inspection

import com.asyncapi.plugin.idea.extensions.index.v2.AsyncAPISpecificationIndex as AsyncAPISpecificationIndexV2
import com.asyncapi.plugin.idea.extensions.index.v3.AsyncAPISpecificationIndex as AsyncAPISpecificationIndexV3
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

        return v2IndexedAsyncAPISpecifications(psiFile).contains(psiFile.virtualFile?.path)
                || v3IndexedAsyncAPISpecifications(psiFile).contains(psiFile.virtualFile?.path)
    }

    fun isAsyncAPIYamlSpecification(psiFile: PsiFile?): Boolean {
        psiFile ?: return false
        if (psiFile !is YAMLFile) {
            return false
        }

        return v2IndexedAsyncAPISpecifications(psiFile).contains(psiFile.virtualFile?.path)
                || v3IndexedAsyncAPISpecifications(psiFile).contains(psiFile.virtualFile?.path)
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

        return v2IndexedAsyncAPISpecificationReferences(psiFile).contains(psiFile.virtualFile?.path)
                || v3IndexedAsyncAPISpecificationReferences(psiFile).contains(psiFile.virtualFile?.path)
    }

    private fun isAsyncAPISpecificationYamlComponent(psiFile: PsiFile?): Boolean {
        psiFile ?: return false
        if (psiFile !is YAMLFile) {
            return false
        }

        return v2IndexedAsyncAPISpecificationReferences(psiFile).contains(psiFile.virtualFile?.path)
                || v3IndexedAsyncAPISpecificationReferences(psiFile).contains(psiFile.virtualFile?.path)
    }

    fun isAsyncAPISpecificationComponent(psiFile: PsiFile?): Boolean {
        psiFile ?: return false
        return when (psiFile) {
            is JsonFile -> return isAsyncAPISpecificationJsonComponent(psiFile)
            is YAMLFile -> return isAsyncAPISpecificationYamlComponent(psiFile)
            else -> false
        }
    }

    private fun v2IndexedAsyncAPISpecifications(asyncapiSpecification: PsiFile): List<String> {
        return FileBasedIndex.getInstance().getValues(
                AsyncAPISpecificationIndexV2.asyncapiIndexId,
                AsyncAPISpecificationIndexV2.asyncapi,
                GlobalSearchScope.allScope(asyncapiSpecification.project)
        ).flatten()
    }

    private fun v3IndexedAsyncAPISpecifications(asyncapiSpecification: PsiFile): List<String> {
        return FileBasedIndex.getInstance().getValues(
            AsyncAPISpecificationIndexV3.asyncapiIndexId,
            AsyncAPISpecificationIndexV3.asyncapi,
            GlobalSearchScope.allScope(asyncapiSpecification.project)
        ).flatten()
    }

    private fun v2IndexedAsyncAPISpecificationReferences(asyncapiSpecification: PsiFile): List<String> {
        return FileBasedIndex.getInstance().getValues(
                AsyncAPISpecificationIndexV2.asyncapiIndexId,
                AsyncAPISpecificationIndexV2.references,
                GlobalSearchScope.allScope(asyncapiSpecification.project)
        ).flatten()
    }

    private fun v3IndexedAsyncAPISpecificationReferences(asyncapiSpecification: PsiFile): List<String> {
        return FileBasedIndex.getInstance().getValues(
            AsyncAPISpecificationIndexV3.asyncapiIndexId,
            AsyncAPISpecificationIndexV3.references,
            GlobalSearchScope.allScope(asyncapiSpecification.project)
        ).flatten()
    }

}