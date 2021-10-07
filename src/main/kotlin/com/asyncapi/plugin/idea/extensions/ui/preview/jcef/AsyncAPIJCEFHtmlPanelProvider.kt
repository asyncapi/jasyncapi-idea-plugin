package com.asyncapi.plugin.idea.extensions.ui.preview.jcef

import com.asyncapi.plugin.idea.extensions.ui.AsyncAPIHtmlPanelProvider
import com.asyncapi.plugin.idea.extensions.ui.AsyncAPIHtmlPanel
import com.intellij.ui.jcef.JBCefApp

class AsyncAPIJCEFHtmlPanelProvider: AsyncAPIHtmlPanelProvider() {

    override fun createHtmlPanel(): AsyncAPIHtmlPanel {
        return AsyncAPIJCEFHtmlPanel()
    }

    override fun isAvailable(): AvailabilityInfo {
        return if (JBCefApp.isSupported()) AvailabilityInfo.AVAILABLE else AvailabilityInfo.UNAVAILABLE
    }

    override fun getProviderInfo(): ProviderInfo {
        return ProviderInfo("JCEF Browser", AsyncAPIJCEFHtmlPanelProvider::class.java.name)
    }

}