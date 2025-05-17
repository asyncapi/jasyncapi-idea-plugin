package com.asyncapi.plugin.idea.extensions.editor

import com.intellij.openapi.actionSystem.DefaultActionGroup

class AsyncAPIEditorActionsGroup: DefaultActionGroup() {

    init {
        getTemplatePresentation().isHideGroupIfEmpty = true;
    }

}