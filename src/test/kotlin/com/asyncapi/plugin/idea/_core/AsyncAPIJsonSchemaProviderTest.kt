package com.asyncapi.plugin.idea._core

import com.intellij.json.psi.JsonFile
import com.intellij.lang.Language
import com.intellij.openapi.components.service
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import org.jetbrains.yaml.YAMLLanguage
import org.jetbrains.yaml.psi.YAMLFile

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
class AsyncAPIJsonSchemaProviderTest: BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    fun `test - injects correctly`() {
        TestCase.assertNotNull(service<AsyncAPIJsonSchemaProvider>())
    }

    fun `test (json) - provide when file is empty`() {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val asyncAPIPSI = psiFileFactory.createFileFromText(
            "asyncapi-2.0.0.json",
            Language.findLanguageByID("JSON")!!,
            ""
        ) as JsonFile

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        TestCase.assertNull(asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project))
    }

    fun `test (yaml) - provide when file is empty`() {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val asyncAPIPSI = psiFileFactory.createFileFromText(
            "asyncapi-2.0.0.yaml",
            YAMLLanguage.INSTANCE,
            ""
        ) as YAMLFile

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        TestCase.assertNull(asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project))
    }

    fun `test (json) - provide when file is not AsyncAPI specification`() {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val asyncAPIPSI = psiFileFactory.createFileFromText(
            "asyncapi-2.0.0.json",
            Language.findLanguageByID("JSON")!!,
            """
                {
                    "type": "AsyncAPI specification"
                }
            """.trimIndent()
        ) as JsonFile

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        TestCase.assertNull(asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project))
    }

    fun `test (yaml) - provide when file is not AsyncAPI specification`() {
        val psiFileFactory = PsiFileFactory.getInstance(project)
        val asyncAPIPSI = psiFileFactory.createFileFromText(
            "asyncapi-2.0.0.yaml",
            YAMLLanguage.INSTANCE,
            """
            type: AsyncAPI specification
            """.trimIndent()
        ) as YAMLFile

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        TestCase.assertNull(asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project))
    }

    fun `test (yaml) - provide when file is 2_0_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.0.0", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.0.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (yaml) - provide when file is 2_1_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.1.0", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.1.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (yaml) - provide when file is 2_2_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.2.0", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.2.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (yaml) - provide when file is 2_3_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.3.0", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.3.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (yaml) - provide when file is 2_4_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.4.0", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.4.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (yaml) - provide when file is 2_5_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.5.0", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.5.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (yaml) - provide when file is 2_6_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.6.0", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.6.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (yaml) - provide when file is 3_0_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-3.0.0", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-3.0.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (yaml) - v2 provide when file is AsyncAPI specification with unknown version`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.99.99", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNull(jsonSchemaObject)
    }

    fun `test (yaml) - v3 provide when file is AsyncAPI specification with unknown version`() {
        val asyncAPIPSI = prepareFile("asyncapi-3.99.99", false)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNull(jsonSchemaObject)
    }

    fun `test (json) - provide when file is 2_0_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.0.0", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.0.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (json) - provide when file is 2_1_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.1.0", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.1.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (json) - provide when file is 2_2_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.2.0", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.2.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (json) - provide when file is 2_3_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.3.0", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.3.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (json) - provide when file is 2_4_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.4.0", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.4.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (json) - provide when file is 2_5_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.5.0", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.5.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (json) - provide when file is 2_6_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.6.0", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-2.6.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (json) - provide when file is 3_0_0 AsyncAPI specification`() {
        val asyncAPIPSI = prepareFile("asyncapi-3.0.0", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNotNull(jsonSchemaObject)
        TestCase.assertEquals("/schema/asyncapi-3.0.0.json", jsonSchemaObject!!.fileUrl!!.takeLast(27))
    }

    fun `test (json) - v2 provide when file is AsyncAPI specification with unknown version`() {
        val asyncAPIPSI = prepareFile("asyncapi-2.99.99", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNull(jsonSchemaObject)
    }

    fun `test (json) - v3 provide when file is AsyncAPI specification with unknown version`() {
        val asyncAPIPSI = prepareFile("asyncapi-3.99.99", true)
        TestCase.assertNotNull(asyncAPIPSI)

        val asyncAPIJsonSchemaProvider = service<AsyncAPIJsonSchemaProvider>()

        val jsonSchemaObject = asyncAPIJsonSchemaProvider.provide(asyncAPIPSI, project)
        TestCase.assertNull(jsonSchemaObject)
    }

    private fun prepareFile(fileName: String, isJson: Boolean ): PsiFile {
        val psiFileFactory = PsiFileFactory.getInstance(project)

        val name = if (isJson) {
            "$fileName.json"
        } else {
            "$fileName.yaml"
        }

        val asyncAPISpecification = this.javaClass.getResource("/$name").readText()

        return if (isJson) {
            psiFileFactory.createFileFromText(
                name,
                Language.findLanguageByID("JSON")!!,
                asyncAPISpecification
            ) as JsonFile
        } else {
            psiFileFactory.createFileFromText(
                name,
                YAMLLanguage.INSTANCE,
                asyncAPISpecification
            ) as YAMLFile
        }
    }

}