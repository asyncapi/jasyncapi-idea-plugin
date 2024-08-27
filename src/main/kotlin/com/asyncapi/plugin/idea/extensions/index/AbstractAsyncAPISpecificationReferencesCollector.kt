package com.asyncapi.plugin.idea.extensions.index

import com.asyncapi.plugin.idea._core.xpath.JsonFileXPath
import com.asyncapi.plugin.idea._core.xpath.YamlFileXPath
import com.intellij.json.psi.JsonFile
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import org.jetbrains.yaml.psi.YAMLFile

/**
 * @author Pavel Bodiachevskii
 */
abstract class AbstractAsyncAPISpecificationReferencesCollector(
        private val asyncAPISpecification: PsiFile,
        private val asyncAPISpecificationDir: VirtualFile,
) {

    abstract fun possibleReferencesLocation(): Map<String, Set<String>>

    fun collectFiles(): Map<String, Set<String>> {
        val references = mutableMapOf<String, Set<String>>()
        possibleReferencesLocation().forEach { (referenceLocation, xpaths) ->
            references[referenceLocation] = xpaths.flatMap { collect(it) }
                    .asSequence()
                    .filter { isFileReference(it) }
                    .map { cutReferenceToPropertyIfExists(it) }
                    .filter { isJsonOrYaml(it) }
                    .mapNotNull { asyncAPISpecificationDir.findFileByRelativePath(it)?.path }
                    .toSet()
        }

        return references
    }

    private fun collect(xpath: String): List<String> {
        return when (asyncAPISpecification) {
            is JsonFile -> return JsonFileXPath.findText(asyncAPISpecification, xpath)
            is YAMLFile -> return YamlFileXPath.findText(asyncAPISpecification, xpath)
            else -> emptyList()
        }
    }

    private fun isFileReference(reference: String) = !reference.startsWith("#/")

    private fun isJsonOrYaml(fileReference: String): Boolean {
        return fileReference.endsWith(".json") || fileReference.endsWith(".yaml") || fileReference.endsWith(".yml")
    }

    private fun cutReferenceToPropertyIfExists(fileReference: String): String {
        if (fileReference.contains("#/")) {
            return fileReference.substring(0, fileReference.indexOf("#/"))
        }

        return fileReference
    }

}