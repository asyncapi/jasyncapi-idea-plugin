package com.asyncapi.plugin.idea.inspection.v2_4_0

import com.asyncapi.plugin.idea.inspection.AbstractAsyncAPIJsonSchemaInspectionTest

/**
 * @author Pavel Bodiachevskii
 * @since 1.6.0
 */
class AsyncAPIJsonSchemaInspectionTest: AbstractAsyncAPIJsonSchemaInspectionTest() {

    override fun getTestDataPath(): String = "src/test/testData/json/inspection/2.4.0"

}