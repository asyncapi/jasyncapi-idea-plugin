package com.asyncapi.plugin.idea.extensions.ui

import com.intellij.openapi.Disposable
import java.nio.file.Path
import javax.swing.JComponent

interface AsyncAPIHtmlPanel: Disposable {

    fun getComponent(): JComponent

    /**
     * Updates current HTML content with the new one.
     * @param html new HTML content.
     * @param initialScrollOffset Offset in the original document which will be used to initially position preview content.
     * @param documentPath Path to original document. It will be used to resolve resources with relative paths, like images.
     */
    fun setHtml(html: String, initialScrollOffset: Int, documentPath: Path?)

}