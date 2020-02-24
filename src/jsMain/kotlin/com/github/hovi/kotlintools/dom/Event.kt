package com.github.hovi.kotlintools.dom

import org.w3c.dom.events.Event
import kotlin.browser.window

fun <E : Event> debounceEvent(callback: () -> Unit, milliseconds: Int, immediate: Boolean = false): (E) -> Unit {
    var timeout: Int? = null

    return fun(event: E) {
        val firstRun = timeout == null
        timeout?.let { window.clearTimeout(it) }
        timeout = window.setTimeout({
            callback()
        }, milliseconds)
        if (firstRun && immediate) {
            callback()
        }
    }
}