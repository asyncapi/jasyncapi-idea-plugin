package com.asyncapi.plugin.idea.extensions.editor.actions

import com.asyncapi.plugin.idea.extensions.editor.split.SplitFileEditor
import com.asyncapi.plugin.idea.extensions.editor.split.SplitFileEditor.Companion.TEXT_EDITOR_SPLIT_KEY
import com.asyncapi.plugin.idea.extensions.editor.split.SplitFileEditorLayout
import com.intellij.ide.lightEdit.LightEditCompatible
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.DumbAware

internal abstract class BaseChangeSplitLayoutAction protected constructor(
    private val myLayoutToSet: SplitFileEditorLayout?
) : AnAction(), DumbAware, Toggleable, LightEditCompatible {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(e: AnActionEvent) {
        val splitFileEditor = findSplitEditor(e)
        e.presentation.setEnabledAndVisible(splitFileEditor != null)

        if (myLayoutToSet != null && splitFileEditor != null) {
            Toggleable.setSelected(e.presentation, splitFileEditor.currentEditorLayout == myLayoutToSet)
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val splitFileEditor = findSplitEditor(e)

        if (splitFileEditor != null) {
            if (myLayoutToSet == null) {
                splitFileEditor.triggerLayoutChange(true)
            } else {
                splitFileEditor.triggerLayoutChange(myLayoutToSet, true)
                Toggleable.setSelected(e.presentation, true)
            }
        }
    }

    fun findSplitEditor(e: AnActionEvent): SplitFileEditor? {
        val editor = e.getData(PlatformDataKeys.FILE_EDITOR)
        return editor as? SplitFileEditor ?: TEXT_EDITOR_SPLIT_KEY.get(editor)
    }

}