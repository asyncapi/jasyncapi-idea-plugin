package com.asyncapi.plugin.idea.extensions.editor

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.editor.Editor
import com.intellij.util.ui.JBEmptyBorder
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JPanel

class AsyncAPIEditorToolbarPanel(editor: Editor, val targetComponentForActions: JComponent): JPanel(BorderLayout()) {

    init {
        val myLinksPanel = JPanel(BorderLayout())
        val panel = JPanel(BorderLayout())
        panel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5))
        panel.add("East", myLinksPanel)
        panel.minimumSize = Dimension(0, 0)
        this.add("Center", panel)

        val toolbar: ActionToolbar = createToolbarFromGroupId("asyncapi.editor")
        toolbar.targetComponent = targetComponentForActions
        panel.add(toolbar.component)
    }

    private fun createToolbarFromGroupId(groupId: String): ActionToolbar {
        val actionManager = ActionManager.getInstance()
        check(actionManager.isGroup(groupId)) { "$groupId should have been a group" }

        val toolbarGroup = DefaultActionGroup()

        val group = actionManager.getAction(groupId) as DefaultActionGroup
        val children = group.getChildren(actionManager)
        for (child in children) {
            toolbarGroup.addAction(child)
        }

        val editorToolbar = actionManager.createActionToolbar(ActionPlaces.EDITOR_TOOLBAR, toolbarGroup, true)
        val component = editorToolbar.component
        component.setOpaque(false)
        component.setBorder(JBEmptyBorder(0, 2, 0, 2))

        return editorToolbar
    }

}