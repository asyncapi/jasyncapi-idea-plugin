package com.asyncapi.plugin.idea._core

import com.asyncapi.plugin.idea._core.xpath.JsonFileXPath
import com.asyncapi.plugin.idea._core.xpath.YamlFileXPath
import com.intellij.json.JsonFileType
import com.intellij.json.psi.JsonFile
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import org.jetbrains.yaml.YAMLFileType
import org.jetbrains.yaml.psi.YAMLFile

@Service
class AsyncAPISchemaRecognizer {

    fun isSchema(project: Project, file: VirtualFile?, useIndex: Boolean = false): Boolean {
        file ?: return false

        if (file.fileType !is YAMLFileType && file.fileType !is JsonFileType) {
            return false
        }

        return if (useIndex) {
            /*
                TODO: implement.
             */
            false
        } else {
            val psiFile = PsiManager.getInstance(project).findFile(file)

            "2.0.0" == extractAsyncAPIKey(psiFile)
        }
    }

    private fun extractAsyncAPIKey(file: PsiFile?): String? {
        file ?: return null

        val psiXPath = "$.asyncapi"
        return when (file) {
            is JsonFile -> JsonFileXPath.findText(file as? JsonFile, psiXPath).firstOrNull()
            is YAMLFile -> YamlFileXPath.findText(file as? YAMLFile, psiXPath).firstOrNull()
            else -> null
        }
    }

}