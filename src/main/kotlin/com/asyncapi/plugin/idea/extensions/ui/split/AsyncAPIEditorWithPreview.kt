package com.asyncapi.plugin.idea.extensions.ui.split

import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.TextEditorWithPreview
import com.intellij.openapi.util.Key

class AsyncAPIEditorWithPreview(editor: TextEditor, preview: AsyncAPIPreviewFileEditor):
    TextEditorWithPreview(
        editor,
        preview,
        "AsyncAPI HTML Preview",
        Layout.SHOW_EDITOR_AND_PREVIEW,
        false
) {

    init {
        editor.putUserData(PARENT_SPLIT_EDITOR_KEY, this)
        preview.putUserData(PARENT_SPLIT_EDITOR_KEY, this)
        preview.setMainEditor(editor.editor)
    }

    override fun createLeftToolbarActionGroup(): ActionGroup? {
        return null
    }

    companion object {
        val PARENT_SPLIT_EDITOR_KEY: Key<AsyncAPIEditorWithPreview> = Key.create("parentSplit")
    }

}