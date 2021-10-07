package com.asyncapi.plugin.idea.extensions.ui.split

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.TextEditor

class AsyncAPISplitEditorProvider: SplitTextEditorProvider(
    AsyncAPITextEditorProvider(),
    AsyncAPIPreviewFileEditorProvider()
) {

    override fun createSplitEditor(firstEditor: FileEditor, secondEditor: FileEditor): FileEditor {
        require(!(firstEditor !is TextEditor || secondEditor !is AsyncAPIPreviewFileEditor)) { "Main editor should be TextEditor" }
        return AsyncAPIEditorWithPreview(firstEditor, secondEditor)
    }

}