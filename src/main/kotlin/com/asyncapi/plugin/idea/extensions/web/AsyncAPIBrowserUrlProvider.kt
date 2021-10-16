package com.asyncapi.plugin.idea.extensions.web

import com.asyncapi.plugin.idea._core.AsyncAPISchemaRecognizer
import com.intellij.ide.browsers.OpenInBrowserRequest
import com.intellij.ide.browsers.WebBrowserUrlProvider
import com.intellij.openapi.components.service
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Url

class AsyncAPIBrowserUrlProvider: WebBrowserUrlProvider() {

    private val asyncAPISchemaRecognizer = service<AsyncAPISchemaRecognizer>()
    private val staticServer = StaticServer()

    override fun canHandleElement(request: OpenInBrowserRequest): Boolean {
        return asyncAPISchemaRecognizer.isSchema(request.file)
    }

    override fun getUrl(request: OpenInBrowserRequest, file: VirtualFile): Url? {
        return staticServer.getUrl(request, file)
    }

}