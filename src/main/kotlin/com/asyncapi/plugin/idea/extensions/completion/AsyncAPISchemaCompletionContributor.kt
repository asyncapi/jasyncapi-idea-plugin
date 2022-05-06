package com.asyncapi.plugin.idea.extensions.completion

import com.asyncapi.plugin.idea._core.AsyncAPISchemaRecognizer
import com.intellij.codeInsight.completion.*
import com.intellij.openapi.components.service
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.util.ResourceUtil
import com.jetbrains.jsonSchema.ide.JsonSchemaService
import com.jetbrains.jsonSchema.impl.JsonSchemaCompletionContributor

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
class AsyncAPISchemaCompletionContributor: CompletionContributor() {

    private val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

    override fun fillCompletionVariants(parameters: CompletionParameters,
                                        result: CompletionResultSet) {
        if (!asyncAPISchemaRecognizer.isSchema(parameters.originalFile)) {
            return
        }

        val position = parameters.position

        val asyncAPIJsonSchemaURL = ResourceUtil.getResource(javaClass.classLoader, "schema", "asyncapi-2.0.0.json")
        VfsUtil.findFileByURL(asyncAPIJsonSchemaURL)?.let { asyncAPIJsonSchemaFile ->
            val jsonSchemaService = JsonSchemaService.Impl.get(position.project)
            val asyncAPIJsonSchemaObject = jsonSchemaService.getSchemaObjectForSchemaFile(asyncAPIJsonSchemaFile)

            asyncAPIJsonSchemaObject?.let { jsonSchemaObject ->
                JsonSchemaCompletionContributor.doCompletion(parameters, result, jsonSchemaObject, false)
            }
        }
    }

}