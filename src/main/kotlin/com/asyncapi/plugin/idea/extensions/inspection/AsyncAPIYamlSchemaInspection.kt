package com.asyncapi.plugin.idea.extensions.inspection

import com.asyncapi.plugin.idea._core.AsyncAPIJsonSchemaProvider
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.components.service
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.jsonSchema.extension.JsonLikePsiWalker
import com.jetbrains.jsonSchema.impl.JsonComplianceCheckerOptions
import com.jetbrains.jsonSchema.impl.JsonSchemaComplianceChecker
import org.jetbrains.yaml.psi.YAMLFile
import org.jetbrains.yaml.psi.YamlPsiElementVisitor

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPIYamlSchemaInspection: LocalInspectionTool() {

    private val asyncAPISpecificationDetector = AsyncAPISpecificationDetector()
    private val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession): PsiElementVisitor {
        if (!asyncAPISpecificationDetector.isAsyncAPIYamlSchema(holder.file)) {
            return PsiElementVisitor.EMPTY_VISITOR
        }

        return createVisitor(holder, isOnTheFly, session)
    }

    private fun createVisitor(holder: ProblemsHolder,
                              isOnTheFly: Boolean,
                              session: LocalInspectionToolSession
    ): PsiElementVisitor {
        /* without this yaml inspection doesn't work correctly. TODO: to research. */
        val yamlValue = (holder.file as YAMLFile).documents?.get(0)?.topLevelValue
        yamlValue ?: return PsiElementVisitor.EMPTY_VISITOR

        val asyncAPIJsonSchemaObject = asyncAPIJsonSchemaProvider.provide(holder.file, holder.project)
        asyncAPIJsonSchemaObject ?: return PsiElementVisitor.EMPTY_VISITOR

        return object: YamlPsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                /* without this doesn't work inspection correctly. TODO: to research. */
                if (element != yamlValue) {
                    return
                }

                val jsonWalker = JsonLikePsiWalker.getWalker(element, asyncAPIJsonSchemaObject)
                jsonWalker ?: return

                JsonSchemaComplianceChecker(asyncAPIJsonSchemaObject, holder, jsonWalker, session, JsonComplianceCheckerOptions(false))
                        .annotate(element)
            }
        }
    }

}