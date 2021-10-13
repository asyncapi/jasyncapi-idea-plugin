package com.asyncapi.plugin.idea.extensions.ui.preview.jcef

import com.asyncapi.plugin.idea.extensions.ui.AsyncAPIHtmlPanel
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.util.registry.Registry
import com.intellij.ui.jcef.JCEFHtmlPanel
import com.intellij.util.Url
import com.intellij.util.Urls
import org.jetbrains.ide.BuiltInServerManager
import java.nio.file.Path
import java.util.*
import javax.swing.JComponent

class AsyncAPIJCEFHtmlPanel: JCEFHtmlPanel(
    isOffScreenRendering(),
    null,
    getClassUrl()
), AsyncAPIHtmlPanel {

    override fun setHtml(html: String, initialScrollOffset: Int, documentPath: Path?) {
//        super.setHtml(html)
        val port = BuiltInServerManager.getInstance().port
        val address = "http://localhost:$port/asyncapi/render" +
                "?schemaUrl=${html}" +
//                "&projectUrl=$projectUrl" +
//                "&projectName=$projectName" +
                "&_ij_reload=RELOAD_ON_SAVE"

        val url = Urls.parseEncoded(address)
        val a = BuiltInServerManager.getInstance().addAuthToken(Objects.requireNonNull<Url>(url))

        cefBrowser.loadURL(a.toExternalForm())
//        val file = FileDocumentManager.getInstance().getFile(document)
    }

    companion object {

        private fun isOffScreenRendering(): Boolean = Registry.`is`("ide.browser.jcef.markdownView.osr.enabled")

        private fun getClassUrl(): String {
            val url = try {
                val cls = AsyncAPIJCEFHtmlPanel::class.java
                cls.getResource("${cls.simpleName}.class").toExternalForm() ?: error("Failed to get class URL!")
            }
            catch (ignored: Exception) {
                "about:blank"
            }
//            return "$url@${Random.nextInt(Integer.MAX_VALUE)}"
            return "$url@${System.currentTimeMillis()}"
        }

    }

    override fun getComponent(): JComponent {
        return super.getComponent()
    }

}