package com.asyncapi.plugin.idea.extensions.ui.split

import com.asyncapi.plugin.idea.extensions.inspection.AsyncAPISchemaDetector
import com.intellij.ide.scratch.ScratchUtil
import com.intellij.json.JsonFileType
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.yaml.YAMLFileType

class AsyncAPITextEditorProvider: PsiAwareTextEditorProvider() {

    private val asyncApiSchemaDetector = AsyncAPISchemaDetector()

    override fun accept(project: Project, file: VirtualFile): Boolean {
        if (!super.accept(project, file)) {
            return false
        }

        if (file.fileType !is JsonFileType && file.fileType !is YAMLFileType) {
            return false
        }

        val fileContent = String(file.contentsToByteArray())
        return fileContent.contains("asyncapi") || shouldAcceptScratchFile(project, file)
    }

    private fun shouldAcceptScratchFile(project: Project, file: VirtualFile): Boolean {
        val fileContent = String(file.contentsToByteArray())
        return ScratchUtil.isScratch(file) && fileContent.contains("asyncapi")
    }
//
//    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
//        val actualEditor = super.createEditor(project, file)
//        if (actualEditor is TextEditor) {
//            val toolbar = FloatingToolbar(actualEditor.editor, "Markdown.Toolbar.Floating")
//            Disposer.register(actualEditor, toolbar)
//        }
//        return actualEditor
//    }

}