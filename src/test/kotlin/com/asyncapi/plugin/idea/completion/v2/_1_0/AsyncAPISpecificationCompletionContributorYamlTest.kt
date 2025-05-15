package com.asyncapi.plugin.idea.completion.v2._1_0

import com.asyncapi.plugin.idea.completion.v2.AbstractAsyncAPISpecificationCompletionContributorTest
import org.junit.Ignore

@Ignore("Investigate why YAML completion variants differ from JSON variants")
class AsyncAPISpecificationCompletionContributorYamlTest: AbstractAsyncAPISpecificationCompletionContributorTest() {

    override fun fileExtension(): String = "yaml"

    override fun asyncAPIVersion(): String = "2.1.0"

    override val `$ - c` = super.`$ - c`.sorted().map { it.replace("\"", "") }

    override val `$ - components - messages - UserSignedUp - p` = super.`$ - components - messages - UserSignedUp - p`.sorted().map { it.replace("\"", "") }

    override val `$ - info - i` = super.`$ - info - i`.sorted().map { it.replace("\"", "") }

}