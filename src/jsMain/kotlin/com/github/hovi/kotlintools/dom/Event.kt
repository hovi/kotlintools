package com.github.hovi.kotlintools.dom

import org.w3c.dom.events.Event
import kotlin.browser.window

fun <E : Event> debounceEvent(callback: (Event) -> Unit, milliseconds: Int, immediate: Boolean = false): (E) -> Unit {
    var timeout: Int? = null

    return fun(event: E) {
        val firstRun = timeout == null
        timeout?.let { window.clearTimeout(it) }
        timeout = window.setTimeout({
            callback(event)
        }, milliseconds)
        if (firstRun && immediate) {
            callback(event)
        }
    }
}

fun <E : Event> throttleEvent(callback: (Event) -> Unit, milliseconds: Int = 250): (E) -> Unit {
    var inThrottle = false

    return fun(event: E) {
        if (inThrottle) {
            return
        }
        callback(event)
        inThrottle = true
        window.setTimeout({
            inThrottle = false
        }, milliseconds)

    }
}
