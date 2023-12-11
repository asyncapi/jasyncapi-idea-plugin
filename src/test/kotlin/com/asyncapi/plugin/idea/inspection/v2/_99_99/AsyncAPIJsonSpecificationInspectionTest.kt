package com.asyncapi.plugin.idea.inspection.v2._99_99

import com.asyncapi.plugin.idea.inspection.AbstractAsyncAPIJsonSpecificationInspectionTest

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
class AsyncAPIJsonSpecificationInspectionTest: AbstractAsyncAPIJsonSpecificationInspectionTest() {

    override fun getTestDataPath(): String = "src/test/testData/json/inspection/2.99.99"

    override fun specificationWithoutRequiredProperty(): String = "without channels.json"

}