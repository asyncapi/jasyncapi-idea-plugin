package com.asyncapi.plugin.idea.extensions.index.v3

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
            AsyncAPISpecificationIndex.tags to setOf(
                "\$.info.tags.*.\$ref",
                "\$.servers.*.tags.*.\$ref",
                "\$.channels.*.tags.*.\$ref",
                "\$.operations.*.tags.*.\$ref",
                "\$.channels.*.messages.*.tags.*.\$ref",
                "\$.operations.*.messages.*.tags.*.\$ref",
                "\$.components.tags.*.\$ref",
            ),
            AsyncAPISpecificationIndex.externalDocs to setOf(
                "\$.info.externalDocs.\$ref",
                "\$.servers.*.externalDocs.\$ref",
                "\$.channels.*.externalDocs.\$ref",
                "\$.operations.*.externalDocs.\$ref",
                "\$.channels.*.messages.*.externalDocs.\$ref",
                "\$.operations.*.messages.*.externalDocs.\$ref",
                "\$.components.externalDocs.*.\$ref",
            ),
            AsyncAPISpecificationIndex.servers to setOf(
                "\$.servers.*.\$ref",
                "\$.channels.*.servers.*.\$ref",
                "\$.components.servers.*.\$ref",
            ),
            AsyncAPISpecificationIndex.serverVariables to setOf(
                "\$.servers.*.variables.*.\$ref",
                "\$.components.serverVariables.*.\$ref",
            ),
            AsyncAPISpecificationIndex.serverBindings to setOf(
                "\$.servers.*.bindings.*.\$ref",
                "\$.components.serverBindings.*.\$ref",
            ),
            AsyncAPISpecificationIndex.channels to setOf(
                "\$.channels.*.\$ref",
                "\$.operations.*.channel.\$ref",
                "\$.operations.*.reply.*.channel.\$ref",
                "\$.components.replies.*.channel.\$ref",
                "\$.components.channels.*.\$ref",
            ),
            AsyncAPISpecificationIndex.channelParameters to setOf(
                "\$.channels.*.parameters.*.\$ref",
                "\$.components.parameters.*.\$ref",
            ),
            AsyncAPISpecificationIndex.channelBindings to setOf(
                "\$.channels.*.bindings.*.\$ref",
                "\$.components.channelBindings.*.\$ref",
            ),
            AsyncAPISpecificationIndex.operations to setOf(
                "\$.operations.*.\$ref",
                "\$.components.operations.*.\$ref",
            ),
            AsyncAPISpecificationIndex.operationBindings to setOf(
                "\$.operations.*.bindings.*.\$ref",
                "\$.components.operationBindings.*.\$ref",
                ),
            AsyncAPISpecificationIndex.operationTraits to setOf(
                "\$.operations.*.traits.*.\$ref",
                "\$.components.operationTraits.*.\$ref",
            ),
            AsyncAPISpecificationIndex.operationReplies to setOf(
                "\$.operations.*.reply.*.\$ref",
                "\$.components.replies.*.\$ref",
            ),AsyncAPISpecificationIndex.operationReplyAddresses to setOf(
                "\$.operations.*.reply.*.address.\$ref",
                "\$.components.replies.*.address.\$ref",
            ),
            AsyncAPISpecificationIndex.messages to setOf(
                "\$.channels.*.messages.*.\$ref",
                "\$.operations.*.messages.*.\$ref",
                "\$.operations.*.reply.*.messages.*.\$ref",
                "\$.components.replies.*.messages.*.\$ref",
                "\$.components.messages.*.\$ref",
            ),
            AsyncAPISpecificationIndex.messageHeaders to setOf(
                "\$.channels.*.messages.*.headers.\$ref",
                "\$.operations.*.messages.*.headers.\$ref",
            ),
            AsyncAPISpecificationIndex.messagePayloads to setOf(
                "\$.channels.*.messages.*.payload.\$ref",
                "\$.operations.*.messages.*.payload.\$ref",
            ),
            AsyncAPISpecificationIndex.messageCorrelationIds to setOf(
                "\$.channels.*.messages.*.correlationId.\$ref",
                "\$.operations.*.messages.*.correlationId.\$ref",
                "\$.components.correlationIds.*.\$ref",
            ),
            AsyncAPISpecificationIndex.messageBindings to setOf(
                "\$.channels.*.messages.*.bindings.*.\$ref",
                "\$.operations.*.messages.*.bindings.*.\$ref",
                "\$.components.messageBindings.*.\$ref",
            ),
            AsyncAPISpecificationIndex.messageTraits to setOf(
                "\$.channels.*.messages.*.traits.*.\$ref",
                "\$.operations.*.messages.*.traits.*.\$ref",
                "\$.components.messageTraits.*.\$ref",
            ),
            AsyncAPISpecificationIndex.securitySchemes to setOf(
                "\$.servers.*.security.*.\$ref",
                "\$.operations.*.security.*.\$ref",
                "\$.components.securitySchemes.*.\$ref",
            ),
            AsyncAPISpecificationIndex.schemas to setOf(
                "\$.components.schemas.*.\$ref",
            ),
        )
    }

}