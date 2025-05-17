package com.asyncapi.plugin.idea.extensions.editor.ui

import com.intellij.openapi.Disposable
import javax.swing.JComponent

interface AsyncAPIHtmlPanel: Disposable {

    fun getComponent(): JComponent

    fun setHtml(html: String)

}