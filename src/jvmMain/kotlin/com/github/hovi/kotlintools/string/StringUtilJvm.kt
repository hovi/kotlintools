package com.github.hovi.kotlintools.string

import com.github.hovi.kotlintools.collection.UTF_INVALID_CHARACTER_BYTES
import java.text.Normalizer
import kotlin.text.Charsets.UTF_8

fun String.removeAccentsJvm(): String {
    return Normalizer.normalize(
        this,
        Normalizer.Form.NFD
    ).replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
}

fun forEachUnicodeLetter(block: (String) -> Unit) {
    for (i in 0..65535) {
        if (Character.isDefined(i) && Character.isLetter(i)) {
            //println("${Integer.toHexString(i)}: ${String(Character.toChars(i))}")
            val character = String(Character.toChars(i))
            block(character)
        }
    }
}

val INVALID_CHARACTER = String(UTF_INVALID_CHARACTER_BYTES, UTF_8)

fun String.isIncorrectlyEncoded(): Boolean {
    return contains(INVALID_CHARACTER)
}