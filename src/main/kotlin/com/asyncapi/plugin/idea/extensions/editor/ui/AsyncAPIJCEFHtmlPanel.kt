package com.asyncapi.plugin.idea.extensions.editor.ui

import com.intellij.openapi.editor.Editor
import com.intellij.ui.jcef.JCEFHtmlPanel
import javax.swing.JComponent

class AsyncAPIJCEFHtmlPanel(
    var editor: Editor?,
): AsyncAPIHtmlPanel, JCEFHtmlPanel(null) {

    override fun getComponent(): JComponent = super.getComponent()

    override fun setHtml(html: String) {
        loadHTML(html, cefBrowser.url)
    }

}