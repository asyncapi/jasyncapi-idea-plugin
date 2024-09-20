package com.asyncapi.plugin.idea.extensions.editor.preview

import com.asyncapi.plugin.idea._core.AsyncAPISpecificationRecognizer
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.WeighedFileEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager

class AsyncAPIPreviewEditorProvider: WeighedFileEditorProvider() {

    private val asyncAPISpecificationRecognizer = service<AsyncAPISpecificationRecognizer>()

    override fun accept(project: Project, file: VirtualFile): Boolean {
        return asyncAPISpecificationRecognizer.isSpecification(PsiManager.getInstance(project).findFile(file))
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return AsyncAPIPreviewEditor(file, FileDocumentManager.getInstance().getDocument(file), project)
    }

    override fun getEditorTypeId(): String {
        return "AsyncAPIPreviewEditor"
    }

    override fun getPolicy(): FileEditorPolicy {
        return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
    }

}