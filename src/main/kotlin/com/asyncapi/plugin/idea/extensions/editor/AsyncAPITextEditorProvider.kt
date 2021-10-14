package com.asyncapi.plugin.idea.extensions.editor

import com.asyncapi.plugin.idea._core.AsyncAPISchemaRecognizer
import com.intellij.ide.scratch.ScratchUtil
import com.intellij.openapi.components.service
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class AsyncAPITextEditorProvider: PsiAwareTextEditorProvider() {

    private val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

    override fun accept(project: Project, file: VirtualFile): Boolean {
        if (!super.accept(project, file)) {
            return false
        }

        return asyncAPISchemaRecognizer.isSchema(project, file) || shouldAcceptScratchFile(project, file)
    }

    private fun shouldAcceptScratchFile(project: Project, file: VirtualFile): Boolean {
        return ScratchUtil.isScratch(file) && asyncAPISchemaRecognizer.isSchema(project, file)
    }

}