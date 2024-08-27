package com.asyncapi.plugin.idea.completion.v3._0_0

import com.asyncapi.plugin.idea.completion.v3.AbstractAsyncAPISpecificationCompletionContributorTest

class AsyncAPISpecificationCompletionContributorYamlTest: AbstractAsyncAPISpecificationCompletionContributorTest() {

    override fun fileExtension(): String = "yaml"

    override fun asyncAPIVersion(): String = "3.0.0"

    override val `$ - c` = super.`$ - c`.sorted().map { it.replace("\"", "") }

    override val `$ - components - messages - UserSignedUp - p` = super.`$ - components - messages - UserSignedUp - p`.sorted().map { it.replace("\"", "") }

    override val `$ - info - i` = super.`$ - info - i`.sorted().map { it.replace("\"", "") }

}