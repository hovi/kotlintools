package com.github.hovi.kotlintools.random

import kotlin.random.Random


val UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
val LOWERCASE = UPPERCASE.toLowerCase()
val NUMERIC = "1234567890"

val UPPERCASE_ALPHANUMERIC = UPPERCASE + NUMERIC
val LOWERCASE_ALPHANUMERIC = LOWERCASE + NUMERIC

fun Random.nextString(size: Int = 10, selectFrom: String = UPPERCASE_ALPHANUMERIC): String {
    val result = StringBuilder()
    while (result.length < size) {
        val index = (this.nextFloat() * selectFrom.length).toInt()
        result.append(selectFrom[index])
    }
    return result.toString()
}