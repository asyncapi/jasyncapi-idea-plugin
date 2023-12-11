package com.asyncapi.plugin.idea.extensions.icon

import com.asyncapi.plugin.idea.extensions.inspection.AsyncAPISpecificationDetector
import com.intellij.ide.IconProvider
import com.intellij.json.psi.JsonFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import org.jetbrains.yaml.psi.YAMLFile
import javax.swing.Icon

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPIIconProvider: IconProvider() {

    private val asyncApiSpecificationDetector = AsyncAPISpecificationDetector()

    override fun getIcon(element: PsiElement, flags: Int): Icon? {
        if (element is JsonFile || element is YAMLFile) {
            if (asyncApiSpecificationDetector.isAsyncAPISpecification(element as? PsiFile) ||
                asyncApiSpecificationDetector.isReferencedAsyncAPISchema(element as? PsiFile)
            ) {
                return Icons.ASYNCAPI_ICON
            }
        }

        return null
    }

}