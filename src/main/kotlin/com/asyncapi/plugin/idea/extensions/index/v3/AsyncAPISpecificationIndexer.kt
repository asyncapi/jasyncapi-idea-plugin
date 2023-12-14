package com.asyncapi.plugin.idea.extensions.index.v3

import com.asyncapi.plugin.idea.extensions.index.AbstractAsyncAPISpecificationIndexer
import com.asyncapi.plugin.idea.extensions.index.AbstractAsyncAPISpecificationReferencesCollector
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPISpecificationIndexer: AbstractAsyncAPISpecificationIndexer() {

    override fun asyncapiIndexKey(): String = AsyncAPISpecificationIndex.asyncapi

    override fun referencesIndexKey(): String = AsyncAPISpecificationIndex.references

    override fun asyncAPISpecificationReferencesCollector(
        asyncAPISpecification: PsiFile,
        asyncAPISpecificationDir: VirtualFile
    ): AbstractAsyncAPISpecificationReferencesCollector {
        return AsyncAPISpecificationReferencesCollector(asyncAPISpecification, asyncAPISpecificationDir)
    }

}