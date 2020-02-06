package com.github.hovi.kotlintools.collection

fun <E> MutableList<E>.addAll(vararg items: E) {
    items.forEach {
        add(it)
    }
}