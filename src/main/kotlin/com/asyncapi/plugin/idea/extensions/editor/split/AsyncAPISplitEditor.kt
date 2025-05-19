package com.asyncapi.plugin.idea.extensions.editor.split

import com.asyncapi.plugin.idea.extensions.editor.preview.AsyncAPIPreviewEditor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.pom.Navigatable
import java.beans.PropertyChangeListener

class AsyncAPISplitEditor(
    textEditor: TextEditor,
    previewEditor: AsyncAPIPreviewEditor
) :
    SplitFileEditor(textEditor, previewEditor), TextEditor
{

    override fun adjustEditorsVisibility() {
        super.adjustEditorsVisibility()
//        previewEditor.renderIfVisible()
    }

    override fun getName(): String = "AsyncAPI Preview Editor"

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
        // do nothing
    }

    override fun canNavigateTo(navigatable: Navigatable): Boolean = textEditor.canNavigateTo(navigatable)

    override fun navigateTo(navigatable: Navigatable) {
        textEditor.navigateTo(navigatable)
    }

    override fun getEditor(): Editor = textEditor.editor

}
