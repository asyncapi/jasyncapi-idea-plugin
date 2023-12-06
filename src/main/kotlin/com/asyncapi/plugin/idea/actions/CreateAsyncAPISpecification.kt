package com.asyncapi.plugin.idea.actions

import com.asyncapi.plugin.idea.extensions.icon.Icons
import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory

/**
 * @author Pavel Bodiachevskii
 */
class CreateAsyncAPISpecification: CreateFileFromTemplateAction(
        "AsyncAPI Specification",
        "Create AsyncAPI specification from the specified template",
        Icons.ASYNCAPI_ICON
), DumbAware {

    override fun buildDialog(project: Project, directory: PsiDirectory, builder: CreateFileFromTemplateDialog.Builder) {
        builder
                .setTitle("New API Specification")
                .addKind("AsyncAPI 2 (.yaml)", Icons.ASYNCAPI_ICON, "AsyncAPI schema (yaml).yaml")
                .addKind("AsyncAPI 2 (.json)", Icons.ASYNCAPI_ICON, "AsyncAPI schema (json).json")
                .addKind("AsyncAPI 3 (.yaml)", Icons.ASYNCAPI_ICON, "AsyncAPI schema 3 (yaml).yaml")
                .addKind("AsyncAPI 3 (.json)", Icons.ASYNCAPI_ICON, "AsyncAPI schema 3 (json).json")
    }

    override fun getActionName(directory: PsiDirectory?, newName: String, templateName: String?): String {
        return "Create API Specification $newName"
    }

}