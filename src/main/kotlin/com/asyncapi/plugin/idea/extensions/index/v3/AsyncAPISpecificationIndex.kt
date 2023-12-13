package com.asyncapi.plugin.idea.extensions.index.v3

import com.intellij.json.JsonFileType
import com.intellij.util.indexing.*
import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.DataInputOutputUtil
import com.intellij.util.io.EnumeratorStringDescriptor
import com.intellij.util.io.KeyDescriptor
import org.jetbrains.yaml.YAMLFileType
import java.io.DataInput
import java.io.DataOutput

/**
 * I use [Set] because of cases when specification has multiple references to components.
 * @author Pavel Bodiachevskii
 */
class AsyncAPISpecificationIndex: FileBasedIndexExtension<String, Set<String>>() {

    override fun getName(): ID<String, Set<String>> = asyncapiIndexId

    override fun getIndexer(): DataIndexer<String, Set<String>, FileContent> = AsyncAPISpecificationIndexer()

    override fun getKeyDescriptor(): KeyDescriptor<String> = EnumeratorStringDescriptor.INSTANCE

    override fun getVersion(): Int = 1

    override fun getInputFilter(): FileBasedIndex.InputFilter = FileBasedIndex.InputFilter { file -> file.fileType is JsonFileType || file.fileType is YAMLFileType }

    override fun dependsOnFileContent(): Boolean = true

    override fun getValueExternalizer(): DataExternalizer<Set<String>> = object: DataExternalizer<Set<String>> {

        override fun save(out: DataOutput, value: Set<String>?) {
            DataInputOutputUtil.writeINT(out, value!!.size)
            value.forEach { out.writeUTF(it) }
        }

        override fun read(`in`: DataInput): Set<String> {
            val indexedAsyncAPISpecifications = mutableSetOf<String>()

            val size = DataInputOutputUtil.readINT(`in`)
            for (i in 0 until size) {
                indexedAsyncAPISpecifications.add(`in`.readUTF())
            }

            return indexedAsyncAPISpecifications
        }

    }

    companion object {

        @JvmStatic
        val asyncapiIndexId = ID.create<String, Set<String>>("com.asyncapi.v3")

        @JvmStatic
        val asyncapi = "asyncapi"

        @JvmStatic
        val references = "references"

        @JvmStatic
        val tags = "tags"

        @JvmStatic
        val externalDocs = "externalDocs"

        @JvmStatic
        val servers = "servers"

        @JvmStatic
        val serverVariables = "serverVariables"

        @JvmStatic
        val serverBindings = "serverBindings"

        @JvmStatic
        val channels = "channels"

        @JvmStatic
        val channelParameters = "channelParameters"

        @JvmStatic
        val channelBindings = "channelBindings"

        @JvmStatic
        val operations = "operations"

        @JvmStatic
        val operationBindings = "operationBindings"

        @JvmStatic
        val operationTraits = "operationTraits"

        @JvmStatic
        val operationReplies = "operationReplies"

        @JvmStatic
        val operationReplyAddresses = "operationReplyAddresses"

        @JvmStatic
        val messages = "messages"

        @JvmStatic
        val messageHeaders = "messageHeaders"

        @JvmStatic
        val messagePayloads = "messagePayloads"

        @JvmStatic
        val messageCorrelationIds = "messageCorrelationIds"

        @JvmStatic
        val messageBindings = "messageBindings"

        @JvmStatic
        val messageTraits = "messageTraits"

        @JvmStatic
        val schemas = "schemas"

        @JvmStatic
        val securitySchemes = "securitySchemes"

    }

}