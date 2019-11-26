package com.github.hovi.kotlintools.dom

import org.w3c.dom.*

fun HTMLElement.hide() {
    this.style.display = "none"
}

fun HTMLElement.show(display: String = "block") {
    this.style.display = display
}