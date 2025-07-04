package com.asyncapi.plugin.idea.extensions.editor.preview

import com.asyncapi.plugin.idea.AsyncAPIPluginScope
import com.asyncapi.plugin.idea._core.AsyncAPISpecificationHtmlRenderer
import com.intellij.openapi.application.EDT
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.fileEditor.FileEditorStateLevel
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.UserDataHolder
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.jcef.JBCefBrowser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.beans.PropertyChangeListener
import javax.swing.JComponent
import javax.swing.JPanel

class AsyncAPIPreviewEditor(
    private val project: Project,
    private val file: VirtualFile,
    private val document: Document,
) : UserDataHolder by UserDataHolderBase(), FileEditor {

    /*
        Prepare HTML
     */
    private val specificationHtmlRenderer = service<AsyncAPISpecificationHtmlRenderer>()

    /*
        Render HTML in embedded browser
     */
    private var jbCefBrowser: JBCefBrowser? = null

    private var mainEditor = MutableStateFlow<Editor?>(null)
    private val coroutineScope = AsyncAPIPluginScope.createChildScope(project)
    private val browserWrapper: JPanel = JPanel(BorderLayout()).apply {
            addComponentListener(AttachPanelOnVisibilityChangeListener())
        }

    init {
        document.addDocumentListener(ReparseContentDocumentListener(), this)
        coroutineScope.launch(Dispatchers.EDT) { attachHtmlPanel() }
    }

    fun setMainEditor(editor: Editor) {
        check(mainEditor.value == null)
        mainEditor.value = editor
    }

    private fun attachHtmlPanel() {
        jbCefBrowser = JBCefBrowser()
        browserWrapper.add(jbCefBrowser!!.component, BorderLayout.CENTER)
        if (browserWrapper.isShowing) {
            browserWrapper.validate()
        }
        browserWrapper.repaint()

        updateHtml()
    }

    private fun detachHtmlPanel() {
        val jbCefBrowser = jbCefBrowser
        if (jbCefBrowser != null) {
            browserWrapper.remove(jbCefBrowser.component)
            Disposer.dispose(jbCefBrowser)
            this.jbCefBrowser = null
        }
    }

    private fun updateHtml() {
        val jbCefBrowser = jbCefBrowser ?: return
        jbCefBrowser.loadHTML(safeRender(file, document))
    }

    private fun safeRender(specificationVirtualFile: VirtualFile, document: Document?): String {
        return try {
            specificationHtmlRenderer.render(file, document)
        } catch (e: Exception) {
            e.message ?: "Something went wrong"
        }
    }

    /*
        Overridden logic
     */
    override fun getComponent(): JComponent = browserWrapper

    override fun getPreferredFocusedComponent(): JComponent? = jbCefBrowser?.component

    override fun getFile(): VirtualFile = file

    override fun getName(): String = "AsyncAPI Preview Editor"

    override fun getState(level: FileEditorStateLevel): FileEditorState = FileEditorState.INSTANCE

    override fun dispose() {
        if (jbCefBrowser != null) {
            detachHtmlPanel()
        }

        coroutineScope.cancel()
    }

    private inner class ReparseContentDocumentListener : DocumentListener {
        override fun documentChanged(event: DocumentEvent) {
            coroutineScope.launch(Dispatchers.EDT) { updateHtml() }
        }
    }

    private inner class AttachPanelOnVisibilityChangeListener : ComponentAdapter() {
        override fun componentShown(event: ComponentEvent) {
            if (jbCefBrowser == null) { // BUG HERE
                coroutineScope.launch(Dispatchers.EDT) { attachHtmlPanel() }
            }
        }

        override fun componentHidden(event: ComponentEvent) {
            if (jbCefBrowser != null) {
                detachHtmlPanel()
            }
        }
    }

    companion object {
//        val PREVIEW_BROWSER: Key<WeakReference<MarkdownHtmlPanel>> = Key.create("PREVIEW_BROWSER")
//
//        internal val PREVIEW_POPUP_POINT: DataKey<RelativePoint> = DataKey.create("PREVIEW_POPUP_POINT")
//        internal val PREVIEW_JCEF_PANEL: DataKey<WeakReference<MarkdownJCEFHtmlPanel>> = DataKey.create("PREVIEW_JCEF_PANEL")
    }

    /*
        Default logic
     */

    override fun setState(state: FileEditorState) {}

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {}

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {}

    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = true

}
