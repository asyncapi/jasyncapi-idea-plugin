package com.asyncapi.plugin.idea.extensions.web

import com.asyncapi.plugin.idea._core.xpath.JsonFileXPath
import com.asyncapi.plugin.idea._core.xpath.YamlFileXPath
import com.asyncapi.plugin.idea.extensions.inspection.AsyncAPISchemaDetector
import com.intellij.ide.browsers.OpenInBrowserRequest
import com.intellij.ide.browsers.WebBrowserUrlProvider
import com.intellij.json.psi.JsonFile
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.util.Url
import org.jetbrains.yaml.psi.YAMLFile

class AsyncAPIBrowserUrlProvider: WebBrowserUrlProvider() {

    private val asyncApiSchemaDetector = AsyncAPISchemaDetector()
    private val staticServer = StaticServer()

    override fun canHandleElement(request: OpenInBrowserRequest): Boolean {
        if (request.file is JsonFile) {
            return JsonFileXPath.findText(
                request.file as JsonFile,
                "$.asyncapi",
                false
            ).isNotEmpty()
        } else if (request.file is YAMLFile) {
            return YamlFileXPath.findText(
                request.file as YAMLFile,
                "$.asyncapi",
                false
            ).isNotEmpty()
        }

        return false
    }

    override fun getUrl(request: OpenInBrowserRequest, file: VirtualFile): Url? {
        return staticServer.getUrl(request, file)
    }

}