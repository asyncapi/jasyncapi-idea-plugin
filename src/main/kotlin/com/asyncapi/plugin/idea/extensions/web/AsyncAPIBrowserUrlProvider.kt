package com.asyncapi.plugin.idea.extensions.web

import com.asyncapi.plugin.idea._core.AsyncAPISpecificationRecognizer
import com.intellij.ide.browsers.OpenInBrowserRequest
import com.intellij.ide.browsers.WebBrowserUrlProvider
import com.intellij.openapi.components.service
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.Url

/**
 * @author Pavel Bodiachevskii
 * @since 1.1.0
 */
class AsyncAPIBrowserUrlProvider: WebBrowserUrlProvider() {

    private val asyncAPISpecificationRecognizer = service<AsyncAPISpecificationRecognizer>()
    private val urlProvider = service<UrlProvider>()

    override fun canHandleElement(request: OpenInBrowserRequest): Boolean {
        return asyncAPISpecificationRecognizer.isSpecification(request.file)
    }

    override fun getUrl(request: OpenInBrowserRequest, file: VirtualFile): Url? {
        return urlProvider.render(request, file)
    }

}