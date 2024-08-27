package com.asyncapi.plugin.idea.extensions.completion

import com.asyncapi.plugin.idea._core.AsyncAPIJsonSchemaProvider
import com.asyncapi.plugin.idea._core.AsyncAPISpecificationRecognizer
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.openapi.components.service
import com.jetbrains.jsonSchema.impl.JsonSchemaCompletionContributor

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
class AsyncAPISpecificationCompletionContributor: CompletionContributor() {

    private val asyncAPISpecificationRecognizer = service<AsyncAPISpecificationRecognizer>()
    private val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

    override fun fillCompletionVariants(parameters: CompletionParameters,
                                        result: CompletionResultSet) {
        if (!asyncAPISpecificationRecognizer.isSpecification(parameters.originalFile)) {
            return
        }

        val asyncAPIJsonSchemaObject = asyncAPIJsonSchemaProvider.provide(parameters.originalFile, parameters.position.project)
        asyncAPIJsonSchemaObject ?: return

        JsonSchemaCompletionContributor.doCompletion(parameters, result, asyncAPIJsonSchemaObject, false)
    }

}