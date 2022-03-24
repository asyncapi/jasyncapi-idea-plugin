package com.asyncapi.plugin.idea.extensions.inspection

import com.asyncapi.plugin.idea._core.AsyncAPISchemaRecognizer
import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.json.psi.JsonElementVisitor
import com.intellij.openapi.components.service
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.ResourceUtil
import com.jetbrains.jsonSchema.extension.JsonLikePsiWalker
import com.jetbrains.jsonSchema.ide.JsonSchemaService
import com.jetbrains.jsonSchema.impl.JsonComplianceCheckerOptions
import com.jetbrains.jsonSchema.impl.JsonSchemaComplianceChecker

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPIJsonSchemaInspection: LocalInspectionTool() {

    private val asyncAPISchemaDetector = AsyncAPISchemaDetector()
    private val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession): PsiElementVisitor {
        if (!asyncAPISchemaDetector.isAsyncAPIJsonSchema(holder.file)) {
            return PsiElementVisitor.EMPTY_VISITOR
        }

        val version = asyncAPISchemaRecognizer.extractAsyncAPIKey(holder.file)
        return createVisitor(holder, isOnTheFly, session, version)
    }

    private fun createVisitor(holder: ProblemsHolder,
                              isOnTheFly: Boolean,
                              session: LocalInspectionToolSession,
                              asyncApiVersion: String?
    ): PsiElementVisitor {
        asyncApiVersion ?: return PsiElementVisitor.EMPTY_VISITOR
        if (!asyncAPISchemaRecognizer.isSupported(asyncApiVersion)) {
            return PsiElementVisitor.EMPTY_VISITOR
        }

        val asyncAPIJsonSchemaURL = ResourceUtil.getResource(javaClass.classLoader, "schema", "asyncapi-$asyncApiVersion.json")
        val asyncAPIJsonSchemaFile = VfsUtil.findFileByURL(asyncAPIJsonSchemaURL)
        asyncAPIJsonSchemaFile ?: return PsiElementVisitor.EMPTY_VISITOR

        val jsonSchemaService = JsonSchemaService.Impl.get(holder.project)
        val asyncAPIJsonSchemaObject = jsonSchemaService.getSchemaObjectForSchemaFile(asyncAPIJsonSchemaFile)
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