package com.asyncapi.plugin.idea._core.xpath.v3

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

    fun `test - 3_0_0`() {
        run("asyncapi", "3.0.0")
    }

    fun run(asyncapi: String, version: String) {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val asyncAPISpecification = this.javaClass.getResource("/$asyncapi-$version.yaml").readText()
        val asyncAPIPSI = psiFileFactory.createFileFromText(
                "$asyncapi-$version.yaml",
                Language.findInstance(YAMLLanguage::class.java),
            asyncAPISpecification
        ) as YAMLFile

        collectReferencesToMessages(asyncAPIPSI)
        collectReferencesToChannels(asyncAPIPSI)
        collectAsyncAPI(asyncAPIPSI, version)
    }

    private fun collectReferencesToMessages(asyncAPI: YAMLFile) {
        val userSignedUp = listOf(
            "#/components/messages/UserSignedUp",
        )

        TestCase.assertEquals(userSignedUp, YamlFileXPath.findText(asyncAPI, "$.channels.*.messages.*.\$ref"))
        TestCase.assertEquals(userSignedUp, YamlFileXPath.findText(asyncAPI, "$.channels.userSignedup.messages.*.\$ref"))
    }

    private fun collectReferencesToChannels(asyncAPI: YAMLFile) {
        val userSignedup = listOf(
            "#/channels/userSignedup",
        )

        TestCase.assertEquals(userSignedup, YamlFileXPath.findText(asyncAPI, "$.operations.*.channel.\$ref"))
        TestCase.assertEquals(userSignedup, YamlFileXPath.findText(asyncAPI, "$.operations.processUserSignups.channel.\$ref"))
    }

    private fun collectAsyncAPI(asyncAPI: YAMLFile, version: String) {
        TestCase.assertEquals(listOf(version), YamlFileXPath.findText(asyncAPI, "$.asyncapi"))
    }

}