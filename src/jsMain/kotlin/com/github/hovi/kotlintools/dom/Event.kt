package com.github.hovi.kotlintools.dom

import org.w3c.dom.events.Event
import kotlin.browser.window

fun <E : Event> debounceEvent(callback: (E) -> Unit, milliseconds: Int, immediate: Boolean = false): (E) -> Unit {
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

fun <E : Event> throttleEvent(
    milliseconds: Int,
    finished: () -> Unit = {},
    callback: (E) -> Unit
): (E) -> Unit {
    var inThrottle = false
    return fun(event: E) {
        if (inThrottle) {
            return
        }
        inThrottle = true
        callback(event)
        window.setTimeout({
            finished()
            inThrottle = false
        }, milliseconds)

    }
}
