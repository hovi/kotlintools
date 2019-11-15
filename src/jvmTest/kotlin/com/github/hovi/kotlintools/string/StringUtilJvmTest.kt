package com.github.hovi.kotlintools.string

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals


fun main() {
    val generated = generate()
    File("src/commonMain/kotlin/eu/karelhovorka/tools/string/Accents.kt").writeText(generated)
}

fun generate(): String {
    var unaccented = ""
    var accented = ""
    forEachUnicodeLetter { text ->
        val removedJvm = text.removeAccentsJvm()
        if (text != removedJvm) {
            if (text.length == removedJvm.length) {
                unaccented += removedJvm
                accented += text
            }
        }
    }
    return ("""
package eu.karelhovorka.tools.string

const val accented__ = "$accented"

const val unaccented = "$unaccented"
        """.trimIndent())
}


class StringUtilJvmTest {


    @Test
    fun removeAllUnicodeAccents() {
        forEachUnicodeLetter {
            testTextForAccents(it)
        }
    }

    fun testTextForAccents(text: String) {
        val removedCommon = text.removeAccents()
        val removedJvm = text.removeAccentsJvm()
        if (removedCommon != removedJvm) {
            if (removedCommon.length == removedJvm.length) {
                assertEquals(removedCommon, removedJvm)
            }
        }
    }

}