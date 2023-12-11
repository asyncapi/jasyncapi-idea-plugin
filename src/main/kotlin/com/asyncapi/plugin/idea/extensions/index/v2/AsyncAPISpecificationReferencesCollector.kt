package com.asyncapi.plugin.idea.extensions.index.v2

import com.asyncapi.plugin.idea.extensions.index.AbstractAsyncAPISpecificationReferencesCollector
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile

/**
 * @author Pavel Bodiachevskii
 */
class AsyncAPISpecificationReferencesCollector(
        asyncAPISpecification: PsiFile,
        asyncAPISpecificationDir: VirtualFile,
): AbstractAsyncAPISpecificationReferencesCollector(asyncAPISpecification, asyncAPISpecificationDir) {

    override fun possibleReferencesLocation(): Map<String, Set<String>> {
        return mapOf(
            AsyncAPISpecificationIndex.channels to setOf("\$.channels.*.\$ref"),
            AsyncAPISpecificationIndex.parameters to setOf(
                "\$.channels.*.parameters.*.\$ref",
                "\$.components.parameters.*.\$ref"
            ),
            AsyncAPISpecificationIndex.traits to setOf(
                "\$.channels.*.subscribe.traits.*.\$ref",
                "\$.channels.*.publish.traits.*.\$ref",
                "\$.components.messages.*.traits.*.\$ref"
            ),
            AsyncAPISpecificationIndex.messages to setOf(
                "\$.channels.*.subscribe.message.\$ref",
                "\$.channels.*.publish.message.\$ref",
                "\$.components.messages.*.\$ref"
            ),
            AsyncAPISpecificationIndex.schemas to setOf("\$.components.schemas.*.\$ref"),
            AsyncAPISpecificationIndex.securitySchemes to setOf("\$.components.securitySchemes.*.\$ref"),
            AsyncAPISpecificationIndex.correlationIds to setOf(
                "\$.components.messages.*.correlationId.\$ref",
                "\$.components.messages.*.traits.*.correlationId.\$ref"
            ),
            AsyncAPISpecificationIndex.headers to setOf(
                "\$.components.messages.*.headers.\$ref",
                "\$.components.messages.*.traits.*.headers.\$ref"
            )
        )
    }

}