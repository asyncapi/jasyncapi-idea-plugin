package com.asyncapi.plugin.idea.extensions.ui

import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.openapi.util.Condition
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.xmlb.annotations.Attribute
import javax.swing.JComponent

abstract class AsyncAPIHtmlPanelProvider {

    abstract fun createHtmlPanel(): AsyncAPIHtmlPanel

    abstract fun isAvailable(): AvailabilityInfo

    abstract fun getProviderInfo(): ProviderInfo

    companion object {

        @JvmStatic
        val EP_NAME: ExtensionPointName<AsyncAPIHtmlPanelProvider> = ExtensionPointName.create("com.asyncapi.plugin.idea.extensions.ui.html.panel.provider")

        @JvmStatic
        fun getProviders(): Array<AsyncAPIHtmlPanelProvider> {
            return EP_NAME.extensions
        }

        @JvmStatic
        fun hasAvailableProviders(): Boolean {
            return ContainerUtil.exists(getProviders()
            ) { provider: AsyncAPIHtmlPanelProvider -> provider.isAvailable() === AvailabilityInfo.AVAILABLE }
        }

        fun getAvailableProviders(): List<AsyncAPIHtmlPanelProvider> {
            return ContainerUtil.filter(getProviders()
            ) { provider: AsyncAPIHtmlPanelProvider -> provider.isAvailable() === AvailabilityInfo.AVAILABLE }
        }

    }

    abstract class AvailabilityInfo {

        abstract fun checkAvailability(parentComponent: JComponent): Boolean

        companion object {

            val AVAILABLE: AvailabilityInfo = object : AvailabilityInfo() {
                override fun checkAvailability(parentComponent: JComponent): Boolean {
                    return true
                }
            }

            val UNAVAILABLE: AvailabilityInfo = object : AvailabilityInfo() {
                override fun checkAvailability(parentComponent: JComponent): Boolean {
                    return false
                }
            }

        }

    }

    class ProviderInfo {

        @Attribute("name")
        var name: String
            private set

        @Attribute("className")
        var className: String
            private set

        private constructor() {
            name = ""
            className = ""
        }

        constructor(name: String, className: String) {
            this.name = name
            this.className = className
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false
            val info = other as ProviderInfo
            if (name != info.name) return false
            return className == info.className
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + className.hashCode()
            return result
        }

        override fun toString(): String {
            return name
        }

    }

}