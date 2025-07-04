package com.asyncapi.plugin.idea.extensions.editor.split

import com.asyncapi.plugin.idea.extensions.editor.preview.AsyncAPIPreviewEditor
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.TextEditorWithPreview
import com.intellij.openapi.project.Project

/**
 * Provides editor with preview for AsyncAPI files
 *
 * @param editor file's editor
 * @param preview AsyncAPI preview component
 * @param project current project
 *
 * @since 3.2.0
 * @author Pavel Bodiachevskii <pavelbodyachevskiy@gmail.com>
 */
class AsyncAPIEditorWithPreview(
    private val editor: TextEditor,
    preview: AsyncAPIPreviewEditor,
    private val project: Project
): TextEditorWithPreview(editor, preview, layout = Layout.SHOW_EDITOR_AND_PREVIEW) {

    init {
        preview.setMainEditor(editor.editor)
    }

    override fun onLayoutChange(
        oldValue: Layout?,
        newValue: Layout?
    ) {
        super.onLayoutChange(oldValue, newValue)
        if (newValue == Layout.SHOW_PREVIEW) {
            requestFocusForPreview();
        }
    }

    fun requestFocusForPreview() {
        val preferredComponent = myPreview.preferredFocusedComponent;
        if (preferredComponent != null) {
            preferredComponent.requestFocus();
            return
        }
        myPreview.component.requestFocus();
    }

}