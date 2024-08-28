package com.asyncapi.plugin.idea._core.xpath

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

/**
 * Dummy implementation of XPath for [PsiFile].
 * // TODO: Support collections
 * @author Pavel Bodiachevskii
 */
abstract class PsiFileXPath<AsyncAPISpecification: PsiFile> {

    /**
     * Converts specification reference to PSI XPath.
     * For example: #/components/messages will be compiled as $.components.messages
     *
     * @param specificationReference to compile as PSI xpath.
     */
    fun compileXPath(specificationReference: String): String {
        return specificationReference
                .removePrefix("\"") // double quoted "some text"
                .removeSuffix("\"")
                .removePrefix("\'") // single quoted 'some text'
                .removeSuffix("\'")
                .replace("#/", "")
                .split("/")
                .joinToString(".", "$.", "")
    }

    /**
     * Search for [PsiElement] by given xpath.
     *
     * @param asyncAPISpecification [PsiFile] to inspect.
     * @param psiXPath PSI XPath to execute.
     * @param partialMatch is partial match allowed.
     *
     * @return list of [PsiElement.getText] for each found [PsiElement] or empty.
     */
    abstract fun findText(asyncAPISpecification: AsyncAPISpecification?, psiXPath: String, partialMatch: Boolean = false): List<String>

    /**
     * Search for [PsiElement] text by given xpath.
     *
     * @param asyncAPISpecification [PsiFile] to inspect.
     * @param psiXPath PSI XPath to execute.
     * @param partialMatch is partial match allowed.
     *
     * @return list of found [PsiElement] or empty.
     */
    abstract fun findPsi(asyncAPISpecification: AsyncAPISpecification?, psiXPath: String, partialMatch: Boolean = false): List<PsiElement>

    /**
     * Split psiXPath to tokens.
     *
     * @return list of tokens or empty
     */
    protected fun tokenize(psiXPath: String): List<String> {
        return psiXPath.replace("$.", "").split(".")
    }

}