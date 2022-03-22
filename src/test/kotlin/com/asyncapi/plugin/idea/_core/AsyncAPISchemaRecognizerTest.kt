package com.asyncapi.plugin.idea._core

import com.intellij.json.psi.JsonFile
import com.intellij.lang.Language
import com.intellij.openapi.components.service
import com.intellij.psi.PsiFileFactory
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import org.jetbrains.yaml.YAMLLanguage
import org.jetbrains.yaml.psi.YAMLFile

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

    fun `test - 2_0_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`("asyncapi-2.0.0")
    }

    fun `test - 2_1_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`("asyncapi-2.1.0")
    }

    fun `test - 2_2_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`("asyncapi-2.2.0")
    }

    fun `test - 2_3_0 isSchema(psiFile) when file is schema and not empty`() {
        `test - isSchema(psiFile) when file is schema and not empty`("asyncapi-2.3.0")
    }

    private fun `test - isSchema(psiFile) when file is schema and not empty`(asyncapi: String) {
        val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

        val psiFileFactory = PsiFileFactory.getInstance(project)
        TestCase.assertTrue(
            "must be true in case of non null, non empty json file with `asyncapi` key provided.",
            asyncAPISchemaRecognizer.isSchema(
                (psiFileFactory.createFileFromText(
                    "$asyncapi.json",
                    Language.findLanguageByID("JSON")!!,
                    this.javaClass.getResource("/$asyncapi.json")?.readText() ?: ""
                ) as JsonFile)
            )
        )
        TestCase.assertTrue(
            "must be true in case of non null, non empty yaml file with `asyncapi` key provided.",
            asyncAPISchemaRecognizer.isSchema(
                (psiFileFactory.createFileFromText(
                    "$asyncapi.yaml",
                    YAMLLanguage.INSTANCE,
                    this.javaClass.getResource("/$asyncapi.yaml")?.readText() ?: ""
                ) as YAMLFile)
            )
        )
    }

    fun `test - isSchema(psiFile) when file is empty`() {
        val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()

        val psiFileFactory = PsiFileFactory.getInstance(project)
        TestCase.assertFalse(
            "must be true in case of non null, non empty json file with `asyncapi` key provided.",
            asyncAPISchemaRecognizer.isSchema(
                (psiFileFactory.createFileFromText(
                    "asyncapi-2.0.0.json",
                    Language.findLanguageByID("JSON")!!,
                    ""
                ) as JsonFile)
            )
        )
        TestCase.assertFalse(
            "must be true in case of non null, non empty yaml file with `asyncapi` key provided.",
            asyncAPISchemaRecognizer.isSchema(
                (psiFileFactory.createFileFromText(
                    "asyncapi-2.0.0.yaml",
                    YAMLLanguage.INSTANCE,
                    ""
                ) as YAMLFile)
            )
        )
    }

}