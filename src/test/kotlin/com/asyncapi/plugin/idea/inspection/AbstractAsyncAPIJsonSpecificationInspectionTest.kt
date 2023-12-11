package com.asyncapi.plugin.idea.inspection

import com.asyncapi.plugin.idea.extensions.inspection.AsyncAPIJsonSpecificationInspection
import com.intellij.testFramework.fixtures.BasePlatformTestCase

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
abstract class AbstractAsyncAPIJsonSpecificationInspectionTest: BasePlatformTestCase() {

    override fun getTestDataPath(): String = "src/test/testData/json/inspection/2.0.0"

    fun `test - with extra keys`() {
        myFixture.configureByFile("extra keys.json")
        myFixture.enableInspections(AsyncAPIJsonSpecificationInspection::class.java)

        myFixture.checkHighlighting()
    }

    fun `test - without channels`() {
        myFixture.configureByFile("without channels.json")
        myFixture.enableInspections(AsyncAPIJsonSpecificationInspection::class.java)

        myFixture.checkHighlighting()
    }

}