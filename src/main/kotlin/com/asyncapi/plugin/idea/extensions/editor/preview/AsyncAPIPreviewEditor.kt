package com.asyncapi.plugin.idea.extensions.editor.preview

import com.asyncapi.plugin.idea.extensions.editor.ui.AsyncAPIJCEFHtmlPanel
import com.intellij.codeHighlighting.BackgroundEditorHighlighter
import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.fileEditor.FileEditorStateLevel
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.UserDataHolderBase
import java.awt.LayoutManager
import java.beans.PropertyChangeListener
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.OverlayLayout

class AsyncAPIPreviewEditor(
    private val document: Document?,
    private val project: Project
) :
    UserDataHolderBase(), FileEditor
{

    var editor: Editor? = null

    private val asyncAPIPreviewEditorComponent: JComponent
    private val htmlPanel: AsyncAPIJCEFHtmlPanel = AsyncAPIJCEFHtmlPanel(editor)

    init {
        asyncAPIPreviewEditorComponent = createComponent()

        val documentListenerHandler = object : DocumentListener {
            override fun documentChanged(event: DocumentEvent) {
                if (asyncAPIPreviewEditorComponent.isVisible && asyncAPIPreviewEditorComponent.isDisplayable) {
                    htmlPanel.setHtml("${document?.text}")
                }
            }
        }
        document?.addDocumentListener(documentListenerHandler, this)
    }

    private fun createComponent(): JComponent {
        val previewEditor = JPanel()
        val overlay: LayoutManager = OverlayLayout(previewEditor)
        previewEditor.setLayout(overlay)

        htmlPanel.setHtml("${document?.text}")
        previewEditor.add(htmlPanel.component)

        return previewEditor
    }

    override fun getComponent(): JComponent = asyncAPIPreviewEditorComponent

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
         Disposer.dispose(htmlPanel)
    }

}
