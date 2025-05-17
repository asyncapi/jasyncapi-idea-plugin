package com.asyncapi.plugin.idea.extensions.editor.split

import com.asyncapi.plugin.idea.extensions.editor.AsyncAPIEditorToolbarPanel
import com.asyncapi.plugin.idea.extensions.editor.MyFileEditorState
import com.asyncapi.plugin.idea.extensions.editor.preview.AsyncAPIPreviewEditor
import com.intellij.codeHighlighting.BackgroundEditorHighlighter
import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.IdeFocusManager
import com.intellij.ui.JBSplitter
import java.awt.BorderLayout
import java.beans.PropertyChangeListener
import javax.swing.JComponent
import javax.swing.JPanel

abstract class SplitFileEditor(
    protected val textEditor: TextEditor,
    protected val previewEditor: AsyncAPIPreviewEditor
): UserDataHolderBase(), FileEditor {

    var currentEditorLayout: SplitFileEditorLayout = SplitFileEditorLayout.EDITOR

    private val splitFileEditorComponent: JComponent

    init {
        textEditor.putUserData(TEXT_EDITOR_SPLIT_KEY, this);
        splitFileEditorComponent = createComponent()
    }

    protected open fun adjustEditorsVisibility() {
        textEditor.component.isVisible = currentEditorLayout.showEditor
        previewEditor.component.isVisible = currentEditorLayout.showPreview
    }

    private fun invalidateLayout() {
        adjustEditorsVisibility()
        splitFileEditorComponent.repaint()
    }

    private fun createComponent(): JComponent {
        val splitter = JBSplitter(false, 0.5f, 0.15f, 0.85f)
        splitter.firstComponent = textEditor.component
        splitter.secondComponent = previewEditor.component

        val splitFileEditor = JPanel(BorderLayout());
        splitFileEditor.add(splitter, BorderLayout.CENTER)

        val myToolbarWrapper = AsyncAPIEditorToolbarPanel(textEditor.editor, splitFileEditor)

        val result = JPanel(BorderLayout())
        result.add(myToolbarWrapper, BorderLayout.NORTH)
        result.add(splitFileEditor, BorderLayout.CENTER)

        adjustEditorsVisibility()

        return result
    }

    override fun getComponent(): JComponent = splitFileEditorComponent

    override fun getPreferredFocusedComponent(): JComponent? {
        return if (textEditor.component.isVisible) {
            textEditor.preferredFocusedComponent
        } else {
            previewEditor.preferredFocusedComponent
        }
    }

    override fun getState(level: FileEditorStateLevel): FileEditorState {
        return MyFileEditorState(
            currentEditorLayout.presentationName,
            textEditor.getState(level),
            previewEditor.getState(level)
        )
    }

    override fun setState(state: FileEditorState) {
        if (state !is MyFileEditorState) {
            return
        }

        if (state.firstState !== null) {
            textEditor.setState(state.firstState)
        }

        if (state.secondState !== null) {
            previewEditor.setState(state.secondState)
        }

        invalidateLayout()
    }

    override fun isModified(): Boolean = textEditor.isModified && previewEditor.isModified

    override fun isValid(): Boolean = textEditor.isValid && previewEditor.isValid

    override fun selectNotify() {
        textEditor.selectNotify()
        previewEditor.selectNotify()
    }

    override fun deselectNotify() {
        textEditor.deselectNotify()
        previewEditor.deselectNotify()
    }

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
        // do nothing
    }

    override fun getBackgroundHighlighter(): BackgroundEditorHighlighter? = textEditor.backgroundHighlighter

    override fun getCurrentLocation(): FileEditorLocation? = textEditor.currentLocation

    override fun getStructureViewBuilder(): StructureViewBuilder? = textEditor.structureViewBuilder

    override fun dispose() {
        Disposer.dispose(textEditor)
        Disposer.dispose(previewEditor)
    }

    override fun getFile(): VirtualFile = textEditor.file

    fun triggerLayoutChange(requestFocus: Boolean) {
        val oldValue: Int = currentEditorLayout.ordinal
        val n: Int = SplitFileEditorLayout.entries.size
        val newValue = (oldValue + n - 1) % n

        triggerLayoutChange(SplitFileEditorLayout.entries[newValue], requestFocus)
    }

    fun triggerLayoutChange(newLayout: SplitFileEditorLayout, requestFocus: Boolean) {
        if (currentEditorLayout == newLayout) {
            return
        }

        currentEditorLayout = newLayout
        invalidateLayout()

        if (requestFocus) {
            val focusComponent = preferredFocusedComponent
            if (focusComponent != null) {
                IdeFocusManager.findInstanceByComponent(focusComponent).requestFocus(focusComponent, true)
            }
        }
    }

    companion object {

        @JvmStatic
        val TEXT_EDITOR_SPLIT_KEY = com.intellij.openapi.util.Key<SplitFileEditor>("text-editor.split-key")

    }

}