package com.asyncapi.plugin.idea.extensions.editor.preview

import com.asyncapi.plugin.idea._core.AsyncAPISchemaRecognizer
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.WeighedFileEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class AsyncAPIPreviewEditorProvider: WeighedFileEditorProvider() {

    private val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

    override fun accept(project: Project, file: VirtualFile): Boolean {
        return asyncAPISchemaRecognizer.isSchema(project, file)
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return AsyncAPIPreviewEditor(FileDocumentManager.getInstance().getDocument(file), project)
    }

    override fun getEditorTypeId(): String {
        return "AsyncAPIPreviewEditor"
    }

    override fun getPolicy(): FileEditorPolicy {
        return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
    }

}