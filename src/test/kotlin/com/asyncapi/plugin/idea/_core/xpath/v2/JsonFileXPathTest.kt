package com.asyncapi.plugin.idea._core.xpath.v2

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

    fun `test - 2_0_0`() {
        run("asyncapi", "2.0.0")
    }

    fun `test - 2_1_0`() {
        run("asyncapi", "2.1.0")
    }

    fun `test - 2_2_0`() {
        run("asyncapi", "2.2.0")
    }

    fun `test - 2_3_0`() {
        run("asyncapi", "2.3.0")
    }

    fun `test - 2_4_0`() {
        run("asyncapi", "2.4.0")
    }

    fun `test - 2_5_0`() {
        run("asyncapi", "2.5.0")
    }

    fun `test - 2_6_0`() {
        run("asyncapi", "2.6.0")
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
        collectAsyncAPI(asyncAPIPSI, version)
    }

    private fun collectReferencesToMessages(asyncAPI: JsonFile) {
        val userSignedUp = listOf(
                "#/components/messages/UserSignedUp",
                "#/components/messages/UserSignedUp",
        )
        val userSignedOut = listOf(
                "#/components/messages/UserSignedOut",
                "#/components/messages/UserSignedOut",
        )
        val allReferences = userSignedUp + userSignedOut

        TestCase.assertEquals(allReferences, JsonFileXPath.findText(asyncAPI, "$.channels.*.*.message.\$ref"))
        TestCase.assertEquals(userSignedUp, JsonFileXPath.findText(asyncAPI, "$.channels.user/signedup.*.message.\$ref"))
        TestCase.assertEquals(userSignedOut, JsonFileXPath.findText(asyncAPI, "$.channels.user/signedout.*.message.\$ref"))
    }

    private fun collectAsyncAPI(asyncAPI: JsonFile, version: String) {
        TestCase.assertEquals(listOf(version), JsonFileXPath.findText(asyncAPI, "$.asyncapi"))
    }

}