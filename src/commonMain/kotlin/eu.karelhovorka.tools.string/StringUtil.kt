package eu.karelhovorka.tools.string

const val accented__ = "áäčďéěíĺľňóôöŕšťúůüýřžÁÄČĎÉĚÍĹĽŇÓÔÖŔŠŤÚŮ ÜÝŘŽ"
const val unaccented = "aacdeeillnooorstuuuyrzAACDEEILLNOOORSTUU UYRZ"

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

fun Char.removeAccent(): Char {
    return accentsToNormal.getOrElse(this, { this })
}


fun Char.isVowel(): Boolean {
    return vowels.contains(toLowerCase().removeAccent())
}