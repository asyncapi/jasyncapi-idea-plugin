package com.asyncapi.plugin.idea.extensions.editor.preview

import com.asyncapi.plugin.idea._core.SchemaHelper
import com.asyncapi.plugin.idea.extensions.editor.MyFileEditorState
import com.asyncapi.plugin.idea.extensions.editor.split.AsyncAPISplitEditorProvider
import com.asyncapi.plugin.idea.extensions.ui.AsyncAPIHtmlPanel
import com.asyncapi.plugin.idea.extensions.ui.AsyncAPIHtmlPanelProvider
import com.intellij.codeHighlighting.BackgroundEditorHighlighter
import com.intellij.json.JsonFileType
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.fileEditor.impl.EditorHistoryManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Alarm
import com.intellij.util.containers.ContainerUtil
import com.jetbrains.rd.util.AtomicReference
import java.awt.BorderLayout
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.beans.PropertyChangeListener
import java.util.*
import javax.swing.JComponent
import javax.swing.JPanel

class AsyncAPIPreviewFileEditor(
    private val myProject: Project,
    private val asyncAPISchemaFile: VirtualFile
) : UserDataHolderBase(), FileEditor {

    private val asyncAPISchemaAsDocument: Document? = FileDocumentManager.getInstance().getDocument(asyncAPISchemaFile)

    private var asyncAPIHtmlPanelReference: AtomicReference<AsyncAPIHtmlPanel?> = AtomicReference(null)
    private val asyncAPIHtmlPanelWrapper: JPanel

    private val ideaPooledAlarm = Alarm(Alarm.ThreadToUse.POOLED_THREAD, this)
    private val swingUIAlarm = Alarm(Alarm.ThreadToUse.SWING_THREAD, this)

    private val REQUESTS_LOCK = Any()
    private var lastHtmlOrRefreshRequest: Runnable? = null

    @Volatile
    private var lastRenderedAsyncAPI: String? = null
    private var mainEditor: Editor? = null

    init {
        asyncAPISchemaAsDocument?.addDocumentListener(object : DocumentListener {

            override fun beforeDocumentChange(e: DocumentEvent) {
                ideaPooledAlarm.cancelAllRequests()
            }

            override fun documentChanged(e: DocumentEvent) {
                ideaPooledAlarm.addRequest({ updateHtml(e.document.text) }, PARSING_CALL_TIMEOUT_MS)
            }

        }, this)

        asyncAPIHtmlPanelWrapper = JPanel(BorderLayout())
        asyncAPIHtmlPanelWrapper.addComponentListener(object : ComponentAdapter() {

            override fun componentShown(e: ComponentEvent) {
                swingUIAlarm.addRequest({
                    asyncAPIHtmlPanelReference.get()?.let {
                        attachHtmlPanel()
                    }
                }, 0, ModalityState.stateForComponent(component))
            }

            override fun componentHidden(e: ComponentEvent) {
                swingUIAlarm.addRequest({
                    asyncAPIHtmlPanelReference.get()?.let {
                        detachHtmlPanel()
                    }
                }, 0, ModalityState.stateForComponent(component))
            }
        })
        if (isPreviewShown(myProject, asyncAPISchemaFile)) {
            attachHtmlPanel()
        }
    }

    fun setMainEditor(editor: Editor?) {
        mainEditor = editor
    }

    override fun getComponent(): JComponent {
        return asyncAPIHtmlPanelWrapper
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return asyncAPIHtmlPanelReference.get().let {
            it ?: return null

            it.getComponent()
        }
    }

    override fun getName(): String = "AsyncAPI HTML Preview"

    override fun setState(state: FileEditorState) {
        /*
            do nothing.
         */
    }

    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = true

    override fun selectNotify() {
        asyncAPIHtmlPanelReference.get()?.let {
            updateHtmlPooled()
        }
    }

    override fun deselectNotify() {
        /*
            do nothing.
         */
    }

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {
        /*
            do nothing.
         */
    }

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {
        /*
            do nothing.
         */
    }

    override fun getBackgroundHighlighter(): BackgroundEditorHighlighter? = null

    override fun getCurrentLocation(): FileEditorLocation? = null

    override fun dispose() {
        asyncAPIHtmlPanelReference.get()?.let {
            Disposer.dispose(it)
        }
    }

    private fun retrievePanelProvider(): AsyncAPIHtmlPanelProvider {
        var provider = AsyncAPIHtmlPanelProvider.getProviders().first()

        if (AsyncAPIHtmlPanelProvider.AvailabilityInfo.AVAILABLE !== provider.isAvailable()) {
            provider = Objects.requireNonNull(
                ContainerUtil.find(
                    AsyncAPIHtmlPanelProvider.getProviders()
                ) { p -> p.isAvailable() === AsyncAPIHtmlPanelProvider.AvailabilityInfo.AVAILABLE }
            )
        }

        return provider
    }

    // Is always run from pooled thread
    private fun updateHtml(asyncAPISchema: String? = null) {
        val asyncAPIHtmlPanel = asyncAPIHtmlPanelReference.get()

        if (asyncAPIHtmlPanel == null || asyncAPISchemaAsDocument == null || !asyncAPISchemaFile.isValid || Disposer.isDisposed(this)) {
            return
        }

        val temporalAsyncAPISchemaUrl = SchemaHelper.saveAsTemporalFile(
            asyncAPISchema ?: asyncAPISchemaAsDocument.text,
            asyncAPISchemaFile.fileType is JsonFileType
        )

        // EA-75860: The lines to the top may be processed slowly; Since we're in pooled thread, we can be disposed already.
        if (!asyncAPISchemaFile.isValid || Disposer.isDisposed(this)) {
            return
        }
        synchronized(REQUESTS_LOCK) {
            if (lastHtmlOrRefreshRequest != null) {
                swingUIAlarm.cancelRequest(lastHtmlOrRefreshRequest!!)
            }

            lastHtmlOrRefreshRequest = Runnable label@{
                if (asyncAPIHtmlPanel == null) {
                    return@label
                }

//                if (temporalFile != null && temporalFile != lastRenderedAsyncAPI) {
//                    lastRenderedAsyncAPI = temporalFile
                    val fileSystem = asyncAPISchemaFile.fileSystem
                    asyncAPIHtmlPanel.setHtml(
                        temporalAsyncAPISchemaUrl,
                        mainEditor!!.caretModel.offset,
                        fileSystem.getNioPath(asyncAPISchemaFile)
                    )
//                }
                synchronized(REQUESTS_LOCK) { lastHtmlOrRefreshRequest = null }
            }
            swingUIAlarm.addRequest(
                lastHtmlOrRefreshRequest!!,
                RENDERING_DELAY_MS,
                ModalityState.stateForComponent(component)
            )
        }
    }

    private fun detachHtmlPanel() {
        asyncAPIHtmlPanelReference.get()?.let {
            asyncAPIHtmlPanelWrapper.remove(it.getComponent())
            Disposer.dispose(it)
            asyncAPIHtmlPanelReference.getAndSet(null)
        }
    }

    private fun attachHtmlPanel() {
        asyncAPIHtmlPanelReference.getAndSet(retrievePanelProvider().createHtmlPanel())
        asyncAPIHtmlPanelReference.get()?.let {
            asyncAPIHtmlPanelWrapper.add(it.getComponent(), BorderLayout.CENTER)

            if (asyncAPIHtmlPanelWrapper.isShowing) {
                asyncAPIHtmlPanelWrapper.validate()
            }

            asyncAPIHtmlPanelWrapper.repaint()
            lastRenderedAsyncAPI = null

            updateHtmlPooled()
        }
    }

    private fun updateHtmlPooled() {
        ideaPooledAlarm.cancelAllRequests()
        ideaPooledAlarm.addRequest({updateHtml()}, 0)
    }

    companion object {

        private const val PARSING_CALL_TIMEOUT_MS = 50L
        private const val RENDERING_DELAY_MS = 20L

        private fun isPreviewShown(project: Project, file: VirtualFile): Boolean {
            val provider: AsyncAPISplitEditorProvider = FileEditorProvider.EP_FILE_EDITOR_PROVIDER
                .findExtension(AsyncAPISplitEditorProvider::class.java)
                ?: return true

            val state = EditorHistoryManager.getInstance(project)
                .getState(file, provider) as? MyFileEditorState
                ?: return true

            val layout: String? = state.splitLayout

            return layout == null || layout != "FIRST"
        }

    }

}
