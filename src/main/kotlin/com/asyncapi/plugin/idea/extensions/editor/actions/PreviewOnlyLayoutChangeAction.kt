package com.asyncapi.plugin.idea.extensions.editor.actions

import com.asyncapi.plugin.idea.extensions.editor.split.SplitFileEditorLayout

private open class PreviewOnlyLayoutChangeAction protected constructor() : BaseChangeSplitLayoutAction(SplitFileEditorLayout.PREVIEW)