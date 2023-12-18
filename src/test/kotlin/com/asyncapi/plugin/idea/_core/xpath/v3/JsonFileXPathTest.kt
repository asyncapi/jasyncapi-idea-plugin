package com.asyncapi.plugin.idea._core.xpath.v3

import com.asyncapi.plugin.idea._core.xpath.JsonFileXPath
import com.intellij.json.psi.JsonFile
import com.intellij.lang.Language
import com.intellij.psi.PsiFileFactory
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase

/**
 * @author Pavel Bodiachevskii
 */
class JsonFileXPathTest: BasePlatformTestCase() {

    fun `test - 3_0_0`() {
        run("asyncapi", "3.0.0")
    }

    private fun run(asyncapi: String, version: String) {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val asyncAPISpecification = this.javaClass.getResource("/$asyncapi-$version.json").readText()
        val asyncAPIPSI = psiFileFactory.createFileFromText(
            "$asyncapi-$version.json",
                Language.findLanguageByID("JSON")!!,
            asyncAPISpecification
        ) as JsonFile

        collectReferencesToMessages(asyncAPIPSI)
        collectReferencesToChannels(asyncAPIPSI)
        collectAsyncAPI(asyncAPIPSI, version)
    }

    private fun collectReferencesToMessages(asyncAPI: JsonFile) {
        val userSignedUp = listOf(
                "#/components/messages/UserSignedUp",
        )

        TestCase.assertEquals(userSignedUp, JsonFileXPath.findText(asyncAPI, "$.channels.*.messages.*.\$ref"))
        TestCase.assertEquals(userSignedUp, JsonFileXPath.findText(asyncAPI, "$.channels.userSignedup.messages.*.\$ref"))
    }

    private fun collectReferencesToChannels(asyncAPI: JsonFile) {
        val userSignedup = listOf(
            "#/channels/userSignedup",
        )

        TestCase.assertEquals(userSignedup, JsonFileXPath.findText(asyncAPI, "$.operations.*.channel.\$ref"))
        TestCase.assertEquals(userSignedup, JsonFileXPath.findText(asyncAPI, "$.operations.processUserSignups.channel.\$ref"))
    }

    private fun collectAsyncAPI(asyncAPI: JsonFile, version: String) {
        TestCase.assertEquals(listOf(version), JsonFileXPath.findText(asyncAPI, "$.asyncapi"))
    }

}