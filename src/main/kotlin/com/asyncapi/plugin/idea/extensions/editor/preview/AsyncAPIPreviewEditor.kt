package com.asyncapi.plugin.idea.extensions.editor.preview

import com.intellij.codeHighlighting.BackgroundEditorHighlighter
import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.fileEditor.FileEditorStateLevel
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolderBase
import java.beans.PropertyChangeListener
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class AsyncAPIPreviewEditor(
    private val document: Document?,
    private val project: Project
) :
    UserDataHolderBase(), FileEditor
{

    var editor: Editor? = null

    override fun getComponent(): JComponent {
        val previewEditor = JPanel()
        val mockText = JLabel("AsyncAPI Preview Editor")

        previewEditor.add(mockText)
        return previewEditor
    }

    override fun getPreferredFocusedComponent(): JComponent = getComponent()

    override fun getName(): String = "Preview"

    override fun getState(level: FileEditorStateLevel): FileEditorState = FileEditorState.INSTANCE

    override fun setState(state: FileEditorState) {
        // do nothing
    }

    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = true

    override fun selectNotify() {
        // do nothing
    }

    override fun deselectNotify() {
        // do nothing
    }

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
        // do nothing
    }

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
        // do nothing
    }

    override fun getBackgroundHighlighter(): BackgroundEditorHighlighter? = null

    override fun getCurrentLocation(): FileEditorLocation? = null

    override fun getStructureViewBuilder(): StructureViewBuilder? = null

    override fun dispose() {
        // TODO: dispose component
        // Disposer.dispose(previewEditor)
        // do nothing
    }

}
