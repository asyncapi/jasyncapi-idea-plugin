package com.asyncapi.plugin.idea._core

import com.intellij.openapi.util.io.FileUtil

object SchemaHelper {

    fun saveAsTemporalFile(schema: String, isJson: Boolean): String {
        val suffix = if (isJson) {
            ".json"
        } else {
            ".yaml"
        }

        val tempSchema = FileUtil.createTempFile("jasyncapi-idea-plugin-${System.currentTimeMillis()}", suffix, true)
        tempSchema.writeText(schema, Charsets.UTF_8)

        return tempSchema.path
    }

}