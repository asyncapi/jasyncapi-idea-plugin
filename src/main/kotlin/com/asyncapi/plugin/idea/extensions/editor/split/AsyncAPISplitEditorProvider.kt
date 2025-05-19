package com.asyncapi.plugin.idea.extensions.editor.split

import com.asyncapi.plugin.idea.extensions.editor.preview.AsyncAPIPreviewEditor
import com.asyncapi.plugin.idea.extensions.editor.preview.AsyncAPIPreviewEditorProvider
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider

class AsyncAPISplitEditorProvider: SplitTextEditorProvider(
    PsiAwareTextEditorProvider(),
    AsyncAPIPreviewEditorProvider()
) {

    override fun createSplitEditor(firstEditor: FileEditor, secondEditor: FileEditor): FileEditor {
        require(!(firstEditor !is TextEditor || secondEditor !is AsyncAPIPreviewEditor)) {
            "Main editor should be TextEditor"
        }

        secondEditor.editor = firstEditor.editor
        return AsyncAPISplitEditor(firstEditor, secondEditor)
    }

}