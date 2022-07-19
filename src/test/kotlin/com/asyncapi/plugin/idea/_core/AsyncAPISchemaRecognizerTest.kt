package com.asyncapi.plugin.idea._core

import com.intellij.lang.Language
import com.intellij.openapi.components.service
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import org.jetbrains.yaml.YAMLLanguage

/**
 * @author Pavel Bodiachevskii
 * @since 1.1.0
 */
class AsyncAPISchemaRecognizerTest: BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun `test - injects correctly`() {
        TestCase.assertNotNull(service<AsyncAPISchemaRecognizer>())
    }

    fun `test - isSchema when file is null`() {
        val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

        TestCase.assertFalse(
            "must be false in case of null file.",
            asyncAPISchemaRecognizer.isSchema(null)
        )
    }

    fun `test - extractAsyncAPIKey when file is null`() {
        val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

        TestCase.assertNull(
            "must be false in case of null file.",
            asyncAPISchemaRecognizer.extractAsyncAPIKey(null)
        )
    }

    fun `test - isSupported when file is null`() {
        val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

        TestCase.assertFalse(
            "must be false in case of null version.",
            asyncAPISchemaRecognizer.isSupported(null)
        )
    }

    fun `test (json) - 2_0_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.0.0.json", Language.findLanguageByID("JSON")!!),
            "2.0.0"
        )
    }

    fun `test (yaml) - 2_0_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.0.0.yaml", YAMLLanguage.INSTANCE),
            "2.0.0"
        )
    }

    fun `test (json) - 2_1_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.1.0.json", Language.findLanguageByID("JSON")!!),
            "2.1.0"
        )
    }

    fun `test (yaml) - 2_1_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.1.0.yaml", YAMLLanguage.INSTANCE),
            "2.1.0"
        )
    }

    fun `test (json) - 2_2_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.2.0.json", Language.findLanguageByID("JSON")!!),
            "2.2.0"
        )
    }

    fun `test (yaml) - 2_2_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.2.0.yaml", YAMLLanguage.INSTANCE),
            "2.2.0"
        )
    }

    fun `test (json) - 2_3_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.3.0.json", Language.findLanguageByID("JSON")!!),
            "2.3.0"
        )
    }

    fun `test (yaml) - 2_3_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.3.0.yaml", YAMLLanguage.INSTANCE),
            "2.3.0"
        )
    }

    fun `test (json) - 2_4_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.4.0.json", Language.findLanguageByID("JSON")!!),
            "2.4.0"
        )
    }

    fun `test (yaml) - 2_4_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`(
            createFile("asyncapi-2.4.0.yaml", YAMLLanguage.INSTANCE),
            "2.4.0"
        )
    }

    private fun `test - isSchema(psiFile) when file is schema and not empty`(asyncApiFile: PsiFile, version: String) {
        val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

        TestCase.assertTrue(
            "$version must be supported.",
            asyncAPISchemaRecognizer.isSupported(
                asyncAPISchemaRecognizer.extractAsyncAPIKey(asyncApiFile)
            )
        )
        TestCase.assertEquals(
            "version $version must be recognized correctly",
            version,
            asyncAPISchemaRecognizer.extractAsyncAPIKey(asyncApiFile)
        )
        TestCase.assertTrue(
            "must be true in case of non null, non empty json file with `asyncapi` key provided.",
            asyncAPISchemaRecognizer.isSchema(
                asyncApiFile
            )
        )
    }

    fun `test (json) - 2_0_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.0.0.json", Language.findLanguageByID("JSON")!!, true)
        )
    }

    fun `test (yaml) - 2_0_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.0.0.yaml", YAMLLanguage.INSTANCE, true)
        )
    }

    fun `test (json) - 2_1_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.1.0.json", Language.findLanguageByID("JSON")!!, true)
        )
    }

    fun `test (yaml) - 2_1_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.1.0.yaml", YAMLLanguage.INSTANCE, true)
        )
    }

    fun `test (json) - 2_2_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.2.0.json", Language.findLanguageByID("JSON")!!, true)
        )
    }

    fun `test (yaml) - 2_2_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.2.0.yaml", YAMLLanguage.INSTANCE, true)
        )
    }

    fun `test (json) - 2_3_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.3.0.json", Language.findLanguageByID("JSON")!!, true)
        )
    }

    fun `test (yaml) - 2_3_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.3.0.yaml", YAMLLanguage.INSTANCE, true)
        )
    }

    fun `test (json) - 2_4_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.4.0.json", Language.findLanguageByID("JSON")!!, true)
        )
    }

    fun `test (yaml) - 2_4_0 isSchema(psiFile) when file is empty`() {
        `test - isSchema(psiFile) when file is empty`(
            createFile("asyncapi-2.4.0.yaml", YAMLLanguage.INSTANCE, true)
        )
    }

    private fun `test - isSchema(psiFile) when file is empty`(asyncApiFile: PsiFile) {
        val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

        TestCase.assertFalse(
            "empty file must not be supported.",
            asyncAPISchemaRecognizer.isSupported(
                asyncAPISchemaRecognizer.extractAsyncAPIKey(asyncApiFile)
            )
        )
        TestCase.assertNull(
            "empty file must not be recognized correctly",
            asyncAPISchemaRecognizer.extractAsyncAPIKey(asyncApiFile)
        )
        TestCase.assertFalse(
            "must be false in case of empty file.",
            asyncAPISchemaRecognizer.isSchema(asyncApiFile)
        )
    }

    private fun createFile(asyncApi: String, language: Language, isEmpty: Boolean = false): PsiFile {
        val psiFileFactory = PsiFileFactory.getInstance(project)

        val text = if (isEmpty) {
            ""
        } else {
            this.javaClass.getResource("/$asyncApi")?.readText() ?: ""
        }

        return psiFileFactory.createFileFromText(asyncApi, language, text)
    }

}