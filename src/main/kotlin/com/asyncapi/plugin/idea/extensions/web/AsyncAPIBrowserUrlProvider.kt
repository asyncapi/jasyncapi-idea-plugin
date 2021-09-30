package com.asyncapi.plugin.idea.extensions.web

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
        if (request.file is JsonFile || request.file is YAMLFile) {
            if (asyncApiSchemaDetector.isAsyncAPISchema(request.file as? PsiFile)) {
                return true
            }
        }

        return false
    }

    override fun getUrl(request: OpenInBrowserRequest, file: VirtualFile): Url? {
        return staticServer.getUrl(request, file)
    }

}