package eu.karelhovorka.tools.string

import java.io.File
import java.text.Normalizer
import kotlin.test.Test
import kotlin.test.assertEquals


fun main() {
    val generated = generate()
    File("src/commonMain/kotlin/eu/karelhovorka/tools/string/Accents.kt").writeText(generated)
}

fun generate(): String {
    var unaccented = ""
    var accented = ""
    acrossUnicode { text ->
        val removedJvm = removeAccentsJvm(text)!!
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

private fun acrossUnicode(block: (String) -> Unit) {
    for (i in 0..65535) {
        if (Character.isDefined(i) && Character.isLetter(i)) {
            //println("${Integer.toHexString(i)}: ${String(Character.toChars(i))}")
            val character = String(Character.toChars(i))
            block(character)
        }
    }
}

private fun removeAccentsJvm(text: String): String? {
    return Normalizer.normalize(
        text,
        Normalizer.Form.NFD
    ).replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
}

class StringUtilJvmTest {


    @Test
    fun removeAllUnicodeAccents() {
        acrossUnicode {
            testTextForAccents(it)
        }
    }

    fun testTextForAccents(text: String) {
        val removedCommon = text.removeAccents()
        val removedJvm = removeAccentsJvm(text)!!
        if (removedCommon != removedJvm) {
            if (removedCommon.length == removedJvm.length) {
                assertEquals(removedCommon, removedJvm)
            }
        }
    }

}