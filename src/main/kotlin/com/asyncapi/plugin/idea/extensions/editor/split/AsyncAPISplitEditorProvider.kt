package com.asyncapi.plugin.idea.extensions.editor.split

import com.asyncapi.plugin.idea.extensions.editor.preview.AsyncAPIPreviewEditor
import com.asyncapi.plugin.idea.extensions.editor.preview.AsyncAPIPreviewEditorProvider
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.TextEditorWithPreviewProvider
import com.intellij.openapi.project.currentOrDefaultProject

/**
 * Provides split editor for AsyncAPI files
 *
 * @since 3.0.0
 * @author Pavel Bodiachevskii <pavelbodyachevskiy@gmail.com>
 */
@Suppress("UnstableApiUsage")
class AsyncAPISplitEditorProvider: TextEditorWithPreviewProvider(
    AsyncAPIPreviewEditorProvider()
) {

    override fun createSplitEditor(firstEditor: TextEditor, secondEditor: FileEditor): FileEditor {
        require(secondEditor is AsyncAPIPreviewEditor) {
            "Secondary editor should be AsyncAPIPreviewEditor"
        }

        return AsyncAPIEditorWithPreview(
            firstEditor,
            secondEditor,
            currentOrDefaultProject(firstEditor.editor.project)
        )
    }

}