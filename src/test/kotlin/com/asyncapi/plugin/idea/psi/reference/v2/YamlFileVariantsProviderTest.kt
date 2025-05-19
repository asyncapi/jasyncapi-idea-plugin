package com.asyncapi.plugin.idea.psi.reference.v2

import com.asyncapi.plugin.idea._core.xpath.JsonFileXPath
import com.asyncapi.plugin.idea._core.xpath.YamlFileXPath
import com.asyncapi.plugin.idea.extensions.psi.reference.YamlFileVariantsProvider
import com.intellij.psi.PsiFileFactory
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import org.jetbrains.yaml.YAMLLanguage
import org.jetbrains.yaml.psi.YAMLFile
import org.jetbrains.yaml.psi.YAMLPsiElement

/*
    LightPlatformCodeInsightTestCase
    LightCompletionTestCase
    CodeInsightFixtureTestCase
 */

/**
 * @author Pavel Bodiachevskii
 */
//class JsonSchemaReferenceCompletionTest: LightJavaCodeInsightFixtureTestCase() {
//class JsonSchemaReferenceCompletionTest: CompletionAutoPopupTestCase() {
//class JsonSchemaReferenceCompletionTest: BasePlatformTestCase() {
class YamlFileVariantsProviderTest: BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun `test completion - 2_0_0`() {
        testCompletion("asyncapi-2.0.0")
    }

    fun `test completion - 2_1_0`() {
        testCompletion("asyncapi-2.1.0")
    }

    fun `test completion - 2_2_0`() {
        testCompletion("asyncapi-2.2.0")
    }

    fun `test completion - 2_3_0`() {
        testCompletion("asyncapi-2.3.0")
    }

    fun `test completion - 2_4_0`() {
        testCompletion("asyncapi-2.4.0")
    }

    fun `test completion - 2_5_0`() {
        testCompletion("asyncapi-2.5.0")
    }

    fun `test completion - 2_6_0`() {
        testCompletion("asyncapi-2.6.0")
    }

    fun testCompletion(asyncapi: String) {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val asyncAPISpecification = this.javaClass.getResource("/$asyncapi.yaml").readText()
        val asyncAPIPSI = psiFileFactory.createFileFromText(
                "$asyncapi.yaml",
                YAMLLanguage.INSTANCE,
                asyncAPISpecification
        ) as YAMLFile

        TestCase.assertEquals(listOf("#/components", "#/components/messages"), collectVariants("#/co", asyncAPIPSI))
        TestCase.assertEquals(listOf("#/components", "#/components/messages"), collectVariants("#/comp", asyncAPIPSI))
        TestCase.assertEquals(listOf("#/components", "#/components/messages"), collectVariants("#/components", asyncAPIPSI))
        TestCase.assertEquals(
                listOf(
                        "#/components/messages",
                        "#/components/messages/UserSignedUp",
                        "#/components/messages/UserSignedOut"
                ),
                collectVariants("#/components/m", asyncAPIPSI)
        )
        TestCase.assertEquals(
                listOf(
                        "#/components/messages/UserSignedUp",
                        "#/components/messages/UserSignedUp/payload",
                        "#/components/messages/UserSignedOut",
                        "#/components/messages/UserSignedOut/payload"
                ),
                collectVariants("#/components/messages/u", asyncAPIPSI)
        )

        TestCase.assertEquals(
                listOf(
                        "#/info",
                        "#/info/title",
                        "#/info/version",
                        "#/info/description",
                        "#/asyncapi"
                ),
                collectVariants("#/i", asyncAPIPSI)
        )
        TestCase.assertEquals(
                listOf(
                        "#/info",
                        "#/info/title",
                        "#/info/version",
                        "#/info/description",
                ),
                collectVariants("#/in", asyncAPIPSI)
        )
        TestCase.assertEquals(
                listOf(
                        "#/info",
                        "#/info/title",
                        "#/info/version",
                        "#/info/description",
                ),
                collectVariants("#/inf", asyncAPIPSI)
        )

        TestCase.assertEquals(
                listOf(
                        "#/info",
                        "#/info/title",
                        "#/info/version",
                        "#/info/description",
                        "#/channels",
                        "#/channels/user/signedup",
                        "#/channels/user/signedout",
                        "#/components",
                        "#/components/messages",
                        "#/asyncapi"
                ),
                collectVariants("#/", asyncAPIPSI)
        )

        TestCase.assertTrue("In case of wrong property name variants must be empty", collectVariants("#/qwerty", asyncAPIPSI).isEmpty())
        TestCase.assertTrue("In case of wrong property name variants must be empty", collectVariants("#/123", asyncAPIPSI).isEmpty())
        TestCase.assertTrue("In case of wrong property name variants must be empty", collectVariants("#/$%^^", asyncAPIPSI).isEmpty())
    }

    private fun collectVariants(xpath: String, specification: YAMLFile): List<String> {
        val psiPath = JsonFileXPath.compileXPath(xpath)
        val foundPsiElements = YamlFileXPath.findPsi(specification, psiPath, true)

        return YamlFileVariantsProvider(
                foundPsiElements.filterIsInstance<YAMLPsiElement>(),
                specification.name,
                psiPath
        ).variants().map { it.lookupString }
    }

}