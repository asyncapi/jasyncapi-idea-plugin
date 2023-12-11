package com.asyncapi.plugin.idea.inspection.v2._6_0

import com.asyncapi.plugin.idea.inspection.AbstractAsyncAPIJsonSpecificationInspectionTest

/**
 * @author Pavel Bodiachevskii
 * @since 2.3.0
 */
class AsyncAPIJsonSpecificationInspectionTest: AbstractAsyncAPIJsonSpecificationInspectionTest() {

    override fun getTestDataPath(): String = "src/test/testData/json/inspection/2.6.0"

    override fun specificationWithoutRequiredProperty(): String = "without channels.json"

}