package com.github.hovi.kotlintools.dom

import org.w3c.dom.*
import kotlinx.browser.window

fun HTMLElement.hide() {
    this.style.display = "none"
}

fun HTMLElement.show(display: String = "block") {
    this.style.display = display
}

fun HTMLElement.toggleDisplay(display: String = "block") {
    when (window.getComputedStyle(this).display) {
        "none" -> {
            style.display = display
        }
        else -> {
            style.display = "none"
        }
    }
}