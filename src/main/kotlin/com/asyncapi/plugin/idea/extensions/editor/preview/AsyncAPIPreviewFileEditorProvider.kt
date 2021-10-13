package com.asyncapi.plugin.idea.extensions.editor.preview

import com.asyncapi.plugin.idea.extensions.ui.AsyncAPIHtmlPanelProvider
import com.intellij.json.JsonFileType
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.WeighedFileEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.yaml.YAMLFileType

class AsyncAPIPreviewFileEditorProvider: WeighedFileEditorProvider() {

    override fun accept(project: Project, file: VirtualFile): Boolean {
        if (!AsyncAPIHtmlPanelProvider.hasAvailableProviders()) {
            return false
        }

        if (file.fileType !is JsonFileType && file.fileType !is YAMLFileType) {
            return false
        }

        val content = String(file.contentsToByteArray())
        return content.contains("asyncapi")
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