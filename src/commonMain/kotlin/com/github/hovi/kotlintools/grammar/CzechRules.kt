package com.github.hovi.kotlintools.grammar

const val NBSP_HTML = "&nbsp;"

const val NBSP_UNICODE = "\u00A0"

fun String.predlozkyZalomeni(nonBreakableSpace: String = NBSP_UNICODE): String {
    return replace(" ([aiouksvzAIOUKSVZ]) ".toRegex(), " $1${nonBreakableSpace}")
}