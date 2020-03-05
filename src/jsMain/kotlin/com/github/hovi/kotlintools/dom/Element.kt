package com.github.hovi.kotlintools.dom

import org.w3c.dom.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent
import kotlin.dom.addClass
import kotlin.dom.removeClass


inline fun <reified E : Element> Element.queryHtmlSelectorAll(selectors: String): List<E> {
    return this.querySelectorAll(selectors = selectors).asElementList()
}

inline fun <reified E : Element> Element.queryHtmlSelectorOrNull(selectors: String): E? {
    return this.querySelector(selectors = selectors) as E?
}

inline fun <reified E : Element> Element.queryHtmlSelector(selectors: String): E {
    return this.queryHtmlSelectorOrNull(selectors = selectors)
        ?: error("couldn't find element by selectors '$selectors'")
}

inline fun <reified E : Element> Element.qsAll(selectors: String): List<E> = this.queryHtmlSelectorAll(selectors)
inline fun <reified E : Element> Element.qs(selectors: String): E = this.queryHtmlSelector(selectors)


internal fun <E : Event> retypeCallback(callback: (E) -> Unit): (Event) -> Unit {
    return {
        callback(it.unsafeCast<E>())
    }
}

fun Element.addClickListener(callback: (MouseEvent) -> Unit) {
    return this.addMouseListener(type = "click", callback = callback)
}

fun Element.addMouseListener(type: String, callback: (MouseEvent) -> Unit) {
    return this.addEventListener(type = type, callback = retypeCallback(callback))
}

fun Element.addKeyboardListener(type: String, callback: (KeyboardEvent) -> Unit) {
    return this.addEventListener(type = type, callback = retypeCallback(callback))
}

var Element?.formValue: String
    get() {
        return when (this) {
            is HTMLInputElement -> {
                this.value
            }
            is HTMLSelectElement -> {
                this.value
            }
            is HTMLTextAreaElement -> {
                this.value
            }
            is HTMLButtonElement -> {
                this.value
            }
            is HTMLOptionElement -> {
                this.value
            }
            else -> error("Element '$this' has no value ")
        }
    }
    set(newValue) {
        when (this) {
            is HTMLInputElement -> {
                this.value = newValue
            }
            is HTMLSelectElement -> {
                this.value = newValue
            }
            is HTMLTextAreaElement -> {
                this.value = newValue
            }
            is HTMLButtonElement -> {
                this.value = newValue
            }
            is HTMLOptionElement -> {
                this.value = newValue
            }
            else -> error("Element '$this' cannot set value")
        }
    }

fun Element.scrollIntoView(behavior: String = "smooth", block: String = "start", inline: String = "nearest") {
    val obj = js("{}")
    obj["behavior"] = behavior
    obj["block"] = block
    obj["inline"] = inline
    scrollIntoView(obj)
}

fun Element.isFormElement(): Boolean {
    return tagName.toUpperCase() in arrayOf("INPUT", "SELECT", "TEXTAREA", "BUTTON")
}

fun Element.disable(backup: Boolean = true) {
    if (backup) {
        backupAttributeIfExists("tabindex")
        backupAttributeIfExists("href")
    }
    setAttribute("tabindex", "-1")
    setAttribute("disabled", "true")
    addClass("disabled")
    removeAttribute("href")
}

fun Element.enable(restore: Boolean = true) {
    removeAttribute("tabindex")
    removeAttribute("disabled")
    removeClass("disabled")
    if (restore) {
        restoreAttributeBackup("tabindex")
        restoreAttributeBackup("href")
    }
}

private fun Element.backupAttributeIfExists(attr: String): String? {
    getAttribute(attr)?.let {
        setAttribute("data-attr-backup-$attr", it)
        return it
    }
    return null
}

private fun Element.restoreAttributeBackup(attr: String): String? {
    getAttribute("data-attr-backup-$attr")?.let {
        setAttribute(attr, it)
        return it
    }
    return null
}