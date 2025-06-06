package com.asyncapi.plugin.idea.psi.reference.v3._0_0

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.jetbrains.rhizomedb.lookup


/**
 * @author Pavel Bodiachevskii
 */
class YamlReferenceVariantsTest: BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/yaml/reference/completion/3.0.0"

    fun `test co`() {
        myFixture.configureByFile("co.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue(listOf("#/components", "#/components/messages").containsAll(variants))
    }

    fun `test comp`() {
        myFixture.configureByFile("comp.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue(listOf("#/components", "#/components/messages").containsAll(variants))
    }

    fun `test components`() {
        myFixture.configureByFile("components.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue(listOf("#/components", "#/components/messages").containsAll(variants))
    }

    fun `test components_m`() {
        myFixture.configureByFile("components_m.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue(
                listOf(
                        "#/components/messages",
                        "#/components/messages/UserSignedUp",
                ).containsAll(variants)
        )
    }

    fun `test components_messages_u`() {
        myFixture.configureByFile("components_messages_u.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue(
                listOf(
                        "#/components/messages/UserSignedUp",
                        "#/components/messages/UserSignedUp/payload",
                ).containsAll(variants)
        )
    }

    fun `test i`() {
        myFixture.configureByFile("i.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue(
                listOf(
                        "#/info",
                        "#/info/title",
                        "#/info/version",
                        "#/info/description",
                        "#/asyncapi"
                ).containsAll(variants)
        )
    }

    fun `test in`() {
        myFixture.configureByFile("in.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue(
                listOf(
                        "#/info",
                        "#/info/title",
                        "#/info/version",
                        "#/info/description"
                ).containsAll(variants)
        )
    }

    fun `test inf`() {
        myFixture.configureByFile("inf.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue(
                listOf(
                        "#/info",
                        "#/info/title",
                        "#/info/version",
                        "#/info/description"
                ).containsAll(variants)
        )
    }

    fun `test `() {
        myFixture.configureByFile("_.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue(
                listOf(
                        "#/info",
                        "#/info/title",
                        "#/info/version",
                        "#/info/description",
                        "#/channels",
                        "#/channels/userSignedup",
                        "#/operations",
                        "#/operations/processUserSignups",
                        "#/components",
                        "#/components/messages",
                        "#/asyncapi",
                ).containsAll(variants)
        )
    }

    fun `test qwerty`() {
        myFixture.configureByFile("qwerty.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue("In case of wrong property name variants must be empty", variants.isEmpty())
    }

    fun `test 123`() {
        myFixture.configureByFile("123.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue("In case of wrong property name variants must be empty", variants.isEmpty())
    }

    fun `test $^^`() {
        myFixture.configureByFile("\$^^.yaml")
        myFixture.complete(CompletionType.BASIC, 1)
        val variants = myFixture.lookupElementStrings ?: emptyList()

        assertTrue("In case of wrong property name variants must be empty", variants.isEmpty())
    }

    fun `test referenced file`() {
        val referenceAtCaret = myFixture.getReferenceAtCaretPositionWithAssertion("reference.yaml", "ref.yaml")
        val lookupElement = referenceAtCaret.variants.first() as LookupElement
        assertEquals("./ref.yaml#/reference", lookupElement.lookupString)
    }

}