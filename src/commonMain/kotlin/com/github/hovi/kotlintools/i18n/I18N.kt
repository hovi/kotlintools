package com.github.hovi.kotlintools.i18n


object I18N {

    const val defaultLanguage: String = "cs"

    var currentLang = defaultLanguage

    fun setLang(lang: String?) {
        if (lang != null) {
            currentLang = lang
        }
    }
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
    return transByLang.get(lang)?.get(message) ?: "_$message($lang)_".apply { println("Missing: $this") }
}
