package com.asyncapi.plugin.idea.completion.v2_0_0

import com.asyncapi.plugin.idea.completion.AbstractAsyncAPISpecificationCompletionContributorTest

class AsyncAPISpecificationCompletionContributorJsonTest: AbstractAsyncAPISpecificationCompletionContributorTest() {

    override fun fileExtension(): String = "json"

    override fun asyncAPIVersion(): String = "2.0.0"

}