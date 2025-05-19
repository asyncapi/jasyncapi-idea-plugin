package com.asyncapi.plugin.idea.extensions.editor.actions

import com.asyncapi.plugin.idea.extensions.editor.split.SplitFileEditorLayout

private open class EditorAndPreviewLayoutChangeAction protected constructor() : BaseChangeSplitLayoutAction(SplitFileEditorLayout.EDITOR_AND_PREVIEW)