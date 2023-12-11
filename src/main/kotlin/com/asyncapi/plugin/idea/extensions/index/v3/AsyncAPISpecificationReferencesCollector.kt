package com.asyncapi.plugin.idea.extensions.index.v3

import com.asyncapi.plugin.idea.extensions.index.AbstractAsyncAPISpecificationReferencesCollector
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPISpecificationReferencesCollector(
        asyncAPISpecification: PsiFile,
        asyncAPISpecificationDir: VirtualFile,
): AbstractAsyncAPISpecificationReferencesCollector(asyncAPISpecification, asyncAPISpecificationDir) {

    override fun possibleReferencesLocation(): Map<String, Set<String>> {
        return emptyMap()
    }

}