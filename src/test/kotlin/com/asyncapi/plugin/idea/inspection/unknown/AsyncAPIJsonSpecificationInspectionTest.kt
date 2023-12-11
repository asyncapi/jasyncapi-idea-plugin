package com.asyncapi.plugin.idea.inspection.unknown

import com.asyncapi.plugin.idea.inspection.AbstractAsyncAPIJsonSpecificationInspectionTest

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
class AsyncAPIJsonSpecificationInspectionTest: AbstractAsyncAPIJsonSpecificationInspectionTest() {

    override fun getTestDataPath(): String = "src/test/testData/json/inspection/unknown"

}