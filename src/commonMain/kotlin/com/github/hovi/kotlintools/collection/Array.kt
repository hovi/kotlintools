package com.github.hovi.kotlintools.collection

fun <T> Array<T>.startsWith(anotherArray: Array<T>): Boolean {
    if (anotherArray.size > this.size) {
        return false
    }
    anotherArray.forEachIndexed { index, t ->
        if (this[index] != t) {
            return false
        }
    }
    return true
}