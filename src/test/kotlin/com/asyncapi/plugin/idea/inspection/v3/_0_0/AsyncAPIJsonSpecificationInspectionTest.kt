package com.asyncapi.plugin.idea.inspection.v3._0_0

import com.asyncapi.plugin.idea.inspection.AbstractAsyncAPIJsonSpecificationInspectionTest

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
class AsyncAPIJsonSpecificationInspectionTest: AbstractAsyncAPIJsonSpecificationInspectionTest() {

    override fun getTestDataPath(): String = "src/test/testData/json/inspection/3.0.0"

    override fun specificationWithoutRequiredProperty(): String = "without info.json"

}