package com.asyncapi.plugin.idea.extensions.editor.split

enum class SplitFileEditorLayout(
    val showEditor: Boolean,
    val showPreview: Boolean,
    val presentationName: String,
) {
    FIRST(true, false, "Show editor only"),
    SECOND(false, true, "Show preview only"),
    SPLIT(true, true, "Show editor and preview");

    override fun toString(): String = presentationName
}