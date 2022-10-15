package com.asyncapi.plugin.idea._core

import com.asyncapi.plugin.idea._core.xpath.YamlFileXPath
import com.intellij.lang.Language
import com.intellij.psi.PsiFileFactory
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import org.jetbrains.yaml.YAMLLanguage
import org.jetbrains.yaml.psi.YAMLFile

/**
 * @author Pavel Bodiachevskii
 */
class YamlFileXPathTest: BasePlatformTestCase() {

    fun `test - 2_0_0`() {
        test("asyncapi", "2.0.0")
    }

    fun `test - 2_1_0`() {
        test("asyncapi", "2.1.0")
    }

    fun `test - 2_2_0`() {
        test("asyncapi", "2.2.0")
    }

    fun `test - 2_3_0`() {
        test("asyncapi", "2.3.0")
    }

    fun `test - 2_4_0`() {
        test("asyncapi", "2.4.0")
    }

    fun `test - 2_5_0`() {
        test("asyncapi", "2.5.0")
    }

    fun test(asyncapi: String, version: String) {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val asyncAPISchema = this.javaClass.getResource("/$asyncapi-$version.yaml").readText()
        val asyncAPIPSI = psiFileFactory.createFileFromText(
                "$asyncapi-$version.yaml",
                Language.findInstance(YAMLLanguage::class.java),
                asyncAPISchema
        ) as YAMLFile

        collectReferencesToMessages(asyncAPIPSI)
        collectAsyncAPI(asyncAPIPSI, version)
    }

    private fun collectReferencesToMessages(asyncAPI: YAMLFile) {
        val userSignedUp = listOf(
                "#/components/messages/UserSignedUp",
                "#/components/messages/UserSignedUp",
        )
        val userSignedOut = listOf(
                "#/components/messages/UserSignedOut",
                "#/components/messages/UserSignedOut",
        )
        val allReferences = userSignedUp + userSignedOut

        TestCase.assertEquals(allReferences, YamlFileXPath.findText(asyncAPI, "$.channels.*.*.message.\$ref"))
        TestCase.assertEquals(userSignedUp, YamlFileXPath.findText(asyncAPI, "$.channels.user/signedup.*.message.\$ref"))
        TestCase.assertEquals(userSignedOut, YamlFileXPath.findText(asyncAPI, "$.channels.user/signedout.*.message.\$ref"))
    }

    private fun collectAsyncAPI(asyncAPI: YAMLFile, version: String) {
        TestCase.assertEquals(listOf(version), YamlFileXPath.findText(asyncAPI, "$.asyncapi"))
    }

}