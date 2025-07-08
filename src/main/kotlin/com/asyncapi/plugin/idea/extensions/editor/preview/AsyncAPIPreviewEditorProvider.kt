package com.asyncapi.plugin.idea.extensions.editor.preview

import com.asyncapi.plugin.idea._core.AsyncAPISpecificationRecognizer
import com.intellij.openapi.components.service
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import kotlinx.coroutines.CoroutineScope

/**
 * Provides preview editor for AsyncAPI files
 *
 * @since 3.1.0
 * @author Pavel Bodiachevskii <pavelbodyachevskiy@gmail.com>
 */
class AsyncAPIPreviewEditorProvider: WeighedFileEditorProvider(), AsyncFileEditorProvider {

    private val asyncAPISpecificationRecognizer = service<AsyncAPISpecificationRecognizer>()

    override fun accept(project: Project, file: VirtualFile): Boolean {
        return asyncAPISpecificationRecognizer.isSpecification(PsiManager.getInstance(project).findFile(file))
    }

    override suspend fun createFileEditor(
        project: Project,
        file: VirtualFile,
        document: Document?,
        editorCoroutineScope: CoroutineScope
    ): FileEditor {
        return AsyncAPIPreviewEditor(project,file, document!!)
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return AsyncAPIPreviewEditor(project, file, FileDocumentManager.getInstance().getDocument(file)!!)
    }

    override fun getEditorTypeId(): String = "asyncapi-preview-editor"

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR

}