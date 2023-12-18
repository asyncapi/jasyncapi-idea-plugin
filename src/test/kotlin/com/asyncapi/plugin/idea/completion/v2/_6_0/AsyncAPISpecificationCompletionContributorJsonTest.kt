package com.asyncapi.plugin.idea.completion.v2._6_0

import com.asyncapi.plugin.idea.completion.v2.AbstractAsyncAPISpecificationCompletionContributorTest

class AsyncAPISpecificationCompletionContributorJsonTest: AbstractAsyncAPISpecificationCompletionContributorTest() {

    override fun fileExtension(): String = "json"

    override fun asyncAPIVersion(): String = "2.6.0"

}