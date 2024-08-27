package com.asyncapi.plugin.idea.completion.v2

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
abstract class AbstractAsyncAPISpecificationCompletionContributorTest: BasePlatformTestCase() {

    abstract fun fileExtension(): String

    abstract fun asyncAPIVersion(): String

    override fun getTestDataPath(): String = "src/test/testData/${fileExtension()}/completion/${asyncAPIVersion()}"

    protected open val `$ - c` = listOf(
        "\"channels\"",
        "\"components\"",
        "\"defaultContentType\"",
        "\"externalDocs\""
    )

    protected open val `$ - components - messages - UserSignedUp - p` = listOf(
        "\"contentType\"",
        "\"deprecated\"",
        "\"description\"",
        "\"examples\""
    )

    protected open val `$ - info - i` = listOf(
        "\"description\"",
        "\"license\"",
        "\"termsOfService\"",
        "\"title\"",
        "\"version\""
    )

    fun `test $ - (double quote)c(double quote)`() {
        configureMyFixture("\"c\".${fileExtension()}")

        /*
            TODO: to research why completion returns more variants only in tests. In IDEA everything is ok:
                expected: ["channels", "components", "defaultContentType", "externalDocs"]
                actual: ["channels", "components", "defaultContentType", "externalDocs", "id", "info", "servers", "tags"]
         */
        TestCase.assertTrue((myFixture.lookupElementStrings ?: emptyList<String>()).containsAll(`$ - c`))
    }

    fun `test $ - c`() {
        configureMyFixture("c.${fileExtension()}")

        TestCase.assertEquals(`$ - c`.sorted(), (myFixture.lookupElementStrings?.sorted() ?: emptyList<String>()))
    }

    fun `test $ - components - messages - UserSignedUp - (double quote)p(double quote)`() {
        configureMyFixture("components -> messages -> UserSignedUp -> \"p\".${fileExtension()}")

        /*
            TODO: to research why completion returns more variants only in tests. In IDEA everything is ok:
                expected: ["contentType", "deprecated", "description", "examples"]
                actual: ["$ref", "bindings", "contentType", "correlationId", "deprecated", "description", "examples", "externalDocs", "headers", "name", "oneOf", "schemaFormat", "summary", "tags", "title", "traits"]
         */
        TestCase.assertTrue(
            (myFixture.lookupElementStrings ?: emptyList<String>())
                .containsAll(`$ - components - messages - UserSignedUp - p`)
        )
    }

    fun `test $ - components - messages - UserSignedUp - (double quote)p`() {
        configureMyFixture("components -> messages -> UserSignedUp -> \"p.${fileExtension()}")

        TestCase.assertEquals(
            `$ - components - messages - UserSignedUp - p`.sorted().map { it.replace("\"", "") },
            (myFixture.lookupElementStrings?.sorted() ?: emptyList<String>())
        )
    }

    fun `test $ - components - messages - UserSignedUp - p`() {
        configureMyFixture("components -> messages -> UserSignedUp -> p.${fileExtension()}")

        TestCase.assertEquals(
            `$ - components - messages - UserSignedUp - p`.sorted(),
            (myFixture.lookupElementStrings?.sorted() ?: emptyList<String>())
        )
    }

    fun `test $ - info - (double quote)i(double quote)`() {
        configureMyFixture("info -> \"i\".${fileExtension()}")

        /*
            TODO: to research why completion returns more variants only in tests. In IDEA everything is ok:
                expected: ["description", "license", "termsOfService", "title", "version"]
                actual: ["contact", "description", "license", "termsOfService", "title", "version"]
         */
        TestCase.assertTrue(
            (myFixture.lookupElementStrings ?: emptyList<String>())
                .containsAll(`$ - info - i`)
        )
    }

    fun `test $ - info - i`() {
        configureMyFixture("info -> i.${fileExtension()}")

        TestCase.assertEquals(`$ - info - i`.sorted(), (myFixture.lookupElementStrings?.sorted() ?: emptyList<String>()))
    }

    private fun configureMyFixture(fileName: String) {
        myFixture.configureByFile(fileName)
        myFixture.complete(CompletionType.BASIC, 1)
    }

}