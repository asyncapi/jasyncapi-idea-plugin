package com.asyncapi.plugin.idea._core

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiFile
import com.intellij.util.ResourceUtil
import com.jetbrains.jsonSchema.ide.JsonSchemaService
import com.jetbrains.jsonSchema.impl.JsonSchemaObject

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
@Service
class AsyncAPIJsonSchemaProvider {

    private val asyncAPISpecificationRecognizer = service<AsyncAPISpecificationRecognizer>()

    fun provide(file: PsiFile, project: Project): JsonSchemaObject? {
        val asyncApiVersion = asyncAPISpecificationRecognizer.extractAsyncAPIKey(file)
        asyncApiVersion ?: return null

        if (!asyncAPISpecificationRecognizer.isSupported(asyncApiVersion)) {
            return null
        }

        val asyncAPIJsonSchemaURL = ResourceUtil.getResource(javaClass.classLoader, "schema", "asyncapi-$asyncApiVersion.json")
        val asyncAPIJsonSchemaFile = VfsUtil.findFileByURL(asyncAPIJsonSchemaURL)
        asyncAPIJsonSchemaFile ?: return null

        val jsonSchemaService = JsonSchemaService.Impl.get(project)
        val asyncAPIJsonSchemaObject = jsonSchemaService.getSchemaObjectForSchemaFile(asyncAPIJsonSchemaFile)
        asyncAPIJsonSchemaObject ?: return null

        return asyncAPIJsonSchemaObject
    }

}