package com.github.hovi.kotlintools.dom

import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.fetch.Response
import kotlin.browser.document

external fun encodeURIComponent(encodedURI: String): String

fun downloadStringAsFile(text: String, filename: String, mime: String = "text/plain", charset: String = "utf-8") {
    val dataStr = "data:${mime};charset=${charset}," + encodeURIComponent(text)
    val dlAnchorElem = document.createHtmlElement<HTMLAnchorElement>()
    dlAnchorElem.setAttribute("href", dataStr)
    dlAnchorElem.setAttribute("download", filename)
    document.body!!.appendChild(dlAnchorElem)
    dlAnchorElem.click()
    dlAnchorElem.remove()
}

fun uploadFile(callback: (Response) -> Unit) {
    val input = document.createHtmlElement<HTMLInputElement>()
    input.type = "file"
    input.addEventListener("change", {
        val file = input.files!!.asList().first()
        callback(Response(file))
    }
    )
    input.click()
}

fun uploadFileAsString(callback: (String) -> Unit) {
    uploadFile {
        it.text().then { text ->
            callback(text)
        }

    }
}
