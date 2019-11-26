package com.github.hovi.kotlintools.dom

import org.w3c.dom.*
import kotlin.reflect.KClass

inline fun <reified E : HTMLElement> Document.getHtmlElementById(id: String): E {
    return this.getHtmlElementByIdOrNull(id) ?: error("Could not find element by id $id")
}

inline fun <reified E : HTMLElement> Document.getHtmlElementByIdOrNull(id: String): E? {
    return this.getElementById(id) as E?
}

inline fun <reified E : HTMLElement> Document.ghebID(id: String) = this.getHtmlElementById<E>(id)


inline fun <reified E : HTMLElement> Document.createHtmlElement(localName: String): E {
    return this.createElement(localName) as E
}

inline fun <reified E : HTMLElement> Document.createHtmlElement(): E {
    val tagName = htmlElementClassesToTagNames.getOrElse(E::class) {
        error("Could not find tag name for html element ${E::class}, use createHtmlElement(localName: String) instead to explicitly call tag name")
    }
    return this.createElement(tagName) as E

}

val htmlElementClassesToTagNames = mapOf<KClass<out HTMLElement>, String>(
    HTMLDivElement::class to "div",
    HTMLSpanElement::class to "span",

    HTMLTableElement::class to "table",
    HTMLTableRowElement::class to "tr",
    HTMLTableCellElement::class to "td",

    HTMLInputElement::class to "input",
    HTMLSelectElement::class to "select",
    HTMLOptGroupElement::class to "optgroup",
    HTMLOptionElement::class to "option",
    HTMLTextAreaElement::class to "textarea",
    HTMLButtonElement::class to "button",
    HTMLFormElement::class to "form",
    HTMLLabelElement::class to "label",

    HTMLVideoElement::class to "video",
    HTMLAudioElement::class to "audio",
    HTMLAnchorElement::class to "a",
    HTMLBRElement::class to "br",
    HTMLHRElement::class to "hr",


    HTMLUListElement::class to "ul",
    HTMLOListElement::class to "ol",
    HTMLLIElement::class to "li",

    HTMLLegendElement::class to "legend",
    HTMLFieldSetElement::class to "fieldset",

    HTMLCanvasElement::class to "canvas",

    HTMLFrameElement::class to "frame",
    HTMLFrameSetElement::class to "frameset",
    HTMLIFrameElement::class to "iframe",


    HTMLTimeElement::class to "time",

    HTMLStyleElement::class to "style",
    HTMLLinkElement::class to "link",
    HTMLScriptElement::class to "script",
    HTMLMetaElement::class to "meta",
    HTMLTitleElement::class to "title",
    HTMLBodyElement::class to "body",
    HTMLHeadElement::class to "head",
    HTMLHtmlElement::class to "html",

    HTMLPreElement::class to "pre",
    HTMLImageElement::class to "img",
    HTMLParagraphElement::class to "p"
)