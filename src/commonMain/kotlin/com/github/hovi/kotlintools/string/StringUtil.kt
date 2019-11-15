package com.github.hovi.kotlintools.string


val accentsToNormal: Map<Char, Char> = accented__.mapIndexed { index, c ->
    c to unaccented[index]
}.toMap()

val vowels = arrayOf('a', 'e', 'i', 'o', 'u', 'y')

fun String.removeAccents(): String {
    var result = this
    accented__.forEachIndexed { index, accented ->
        result = result.replace(accented, unaccented[index])
    }
    return result
}

fun String.createId(whitespaceReplacement: String = "-"): String {
    return (this.removeAccents()).toLowerCase().replace("\\s+".toRegex(), whitespaceReplacement)
}

fun Char.removeAccent(): Char {
    return accentsToNormal.getOrElse(this, { this })
}


fun Char.isVowel(): Boolean {
    return vowels.contains(toLowerCase().removeAccent())
}