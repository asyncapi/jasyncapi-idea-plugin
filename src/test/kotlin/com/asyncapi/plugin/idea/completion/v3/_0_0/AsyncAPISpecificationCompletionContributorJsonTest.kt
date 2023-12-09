package com.asyncapi.plugin.idea.completion.v3._0_0

import com.asyncapi.plugin.idea.completion.v3.AbstractAsyncAPISpecificationCompletionContributorTest

/**
 * @author Pavel Bodiachevskii
 * @since 2.4.0
 */
class AsyncAPISpecificationCompletionContributorJsonTest: AbstractAsyncAPISpecificationCompletionContributorTest() {

    override fun fileExtension(): String = "json"

    override fun asyncAPIVersion(): String = "3.0.0"

}