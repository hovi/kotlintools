package com.github.hovi.kotlintools.dom

import org.w3c.dom.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent


inline fun <reified E : Element> E.queryHtmlSelectorAll(selectors: String): List<E> {
    return this.querySelectorAll(selectors = selectors).asElementList()
}

inline fun <reified E : Element> E.queryHtmlSelectorOrNull(selectors: String): E? {
    return this.querySelector(selectors = selectors) as E?
}

inline fun <reified E : Element> E.queryHtmlSelector(selectors: String): E {
    return this.queryHtmlSelectorOrNull(selectors = selectors)
        ?: error("couldn't find element by selectors '$selectors'")
}

inline fun <reified E : Element> E.qsAll(selectors: String): List<E> = this.queryHtmlSelectorAll(selectors)
inline fun <reified E : Element> E.qs(selectors: String): E = this.queryHtmlSelector(selectors)


internal fun <E : Event> retypeCallback(callback: (E) -> Unit): (Event) -> Unit {
    return {
        callback(it.unsafeCast<E>())
    }
}

fun Element.addClickListener(callback: ((MouseEvent) -> Unit)) {
    return this.addEventListener(type = "click", callback = retypeCallback(callback))
}

fun Element.addMouseListener(type: String, callback: ((MouseEvent) -> Unit)) {
    return this.addEventListener(type = type, callback = retypeCallback(callback))
}

fun Element.addKeyboardListener(type: String, callback: ((KeyboardEvent) -> Unit)) {
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