package eu.karelhovorka.tools.string

import java.text.Normalizer

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