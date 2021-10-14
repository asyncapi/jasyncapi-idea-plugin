package com.asyncapi.plugin.idea.extensions.editor.preview

import com.asyncapi.plugin.idea._core.AsyncAPISchemaRecognizer
import com.asyncapi.plugin.idea.extensions.ui.AsyncAPIHtmlPanelProvider
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.WeighedFileEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class AsyncAPIPreviewFileEditorProvider: WeighedFileEditorProvider() {

    private val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

    override fun accept(project: Project, file: VirtualFile): Boolean {
        if (!AsyncAPIHtmlPanelProvider.hasAvailableProviders()) {
            return false
        }

        return asyncAPISchemaRecognizer.isSchema(project, file)
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return AsyncAPIPreviewFileEditor(project, file)
    }

    override fun getEditorTypeId(): String {
        return "asyncapi-preview-editor"
    }

    override fun getPolicy(): FileEditorPolicy {
        return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
    }

}