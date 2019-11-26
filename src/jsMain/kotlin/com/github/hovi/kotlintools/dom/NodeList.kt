package com.github.hovi.kotlintools.dom

import org.w3c.dom.ItemArrayLike
import org.w3c.dom.Node
import org.w3c.dom.ParentNode


fun <T : ParentNode> ItemArrayLike<Node>.asElementList(): List<T> = object : AbstractList<T>() {
    override val size: Int get() = this@asElementList.length

    override fun get(index: Int): T = when (index) {
        in 0..lastIndex -> this@asElementList.item(index).unsafeCast<T>()
        else -> throw IndexOutOfBoundsException("index $index is not in range [0..$lastIndex]")
    }
}