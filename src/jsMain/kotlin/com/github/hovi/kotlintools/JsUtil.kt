package com.github.hovi.kotlintools

fun isBrowser(): Boolean {
    return js("typeof window !== 'undefined'") as Boolean
}