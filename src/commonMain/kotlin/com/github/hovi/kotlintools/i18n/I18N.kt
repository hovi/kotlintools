package com.github.hovi.kotlintools.i18n


object I18N {

    const val defaultLanguage: String = "cs"

    var currentLang = defaultLanguage

}

val transByLang = mutableMapOf<String, MutableMap<String, String>>(
    I18N.defaultLanguage to mutableMapOf(
    )
)

fun addTranslation(lang: String, key: String, value: String) {
    transByLang.getOrPut(lang, { mutableMapOf<String, String>() }).put(key, value)
}

fun addTranslation(lang: String, translations: Map<String, String>) {
    transByLang.getOrPut(lang, { mutableMapOf<String, String>() }).putAll(translations)
}

fun String.i18n(lang: String = I18N.currentLang, vararg arguments: Any?): String {
    return _i18n(this, lang, arguments)
}

private fun _i18n(message: String, lang: String = I18N.currentLang, vararg arguments: Any?): String {
    return transByLang[lang]?.get(message) ?: missingMessage(message, lang, arguments)
}

val notifyMissingTranslation: (String, String, Array<out Any?>) -> String = { message, lang, arguments ->
    "_$message($lang)_".apply { println("Missing: $this") }
}

val defaultToMessage: (String, String, Array<out Any?>) -> String = { message, lang, arguments ->
    "_$message($lang)_".apply { println("Missing: $this") }
}

var missingMessage: (String, String, Array<out Any?>) -> String = notifyMissingTranslation