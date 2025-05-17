package com.asyncapi.plugin.idea.extensions.editor.split

enum class SplitFileEditorLayout(
    val showEditor: Boolean,
    val showPreview: Boolean,
    val presentationName: String,
) {
    EDITOR(true, false, "Editor"),
    PREVIEW(false, true, "Preview"),
    EDITOR_AND_PREVIEW(true, true, "Editor and Preview");

    override fun toString(): String = presentationName
}