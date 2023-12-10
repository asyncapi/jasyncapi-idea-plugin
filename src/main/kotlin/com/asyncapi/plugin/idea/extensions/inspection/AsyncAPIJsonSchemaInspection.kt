package com.asyncapi.plugin.idea.extensions.inspection

import com.asyncapi.plugin.idea._core.AsyncAPIJsonSchemaProvider
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.json.psi.JsonElementVisitor
import com.intellij.openapi.components.service
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.jsonSchema.extension.JsonLikePsiWalker
import com.jetbrains.jsonSchema.impl.JsonComplianceCheckerOptions
import com.jetbrains.jsonSchema.impl.JsonSchemaComplianceChecker

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPIJsonSchemaInspection: LocalInspectionTool() {

    private val asyncAPISpecificationDetector = AsyncAPISpecificationDetector()
    private val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession): PsiElementVisitor {
        if (!asyncAPISpecificationDetector.isAsyncAPIJsonSchema(holder.file)) {
            return PsiElementVisitor.EMPTY_VISITOR
        }

        return createVisitor(holder, isOnTheFly, session)
    }

    private fun createVisitor(holder: ProblemsHolder,
                              isOnTheFly: Boolean,
                              session: LocalInspectionToolSession
    ): PsiElementVisitor {
        val asyncAPIJsonSchemaObject = asyncAPIJsonSchemaProvider.provide(holder.file, holder.project)
        asyncAPIJsonSchemaObject ?: return PsiElementVisitor.EMPTY_VISITOR

        return object: JsonElementVisitor() {
            override fun visitElement(element: PsiElement) {
                val jsonWalker = JsonLikePsiWalker.getWalker(element, asyncAPIJsonSchemaObject)
                jsonWalker ?: return

                JsonSchemaComplianceChecker(asyncAPIJsonSchemaObject, holder, jsonWalker, session, JsonComplianceCheckerOptions(false))
                        .annotate(element)
            }
        }
    }

}