package com.github.hovi.kotlintools.charset


import com.github.hovi.kotlintools.collection.UTF8_BOM
import com.github.hovi.kotlintools.collection.UTF8_INVALID_CHARACTER_BYTES
import com.github.hovi.kotlintools.collection.countSubArray
import com.github.hovi.kotlintools.collection.startsWith
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

const val CZECH_CHARS_LOWER = "říšžťčýůňúěďáéó"

const val CZECH_CHARS_UPPER = "ŘÍŠŽŤČÝŮŇÚĚĎÁÉÓ"

const val CZECH_CHARS = CZECH_CHARS_LOWER + CZECH_CHARS_UPPER

const val SLOVAK_CHARS = "ÄäÔôĹĽĺ" //'ľ' causes problems - has same as 'ž' in  other encoding


data class CharCountResult(val charset: Charset, val score: Double, val priority: Int = 0) :
    Comparable<CharCountResult> {
    override fun compareTo(other: CharCountResult): Int {
        return if (this.score == other.score) {
            other.priority.compareTo(this.priority)
        } else {
            other.score.compareTo(this.score)
        }
    }
}

class UTFCharsetCharCounter(text: String, priority: Int = 10) :
    CharsetCharCounter(charsToDetect = text, charset = UTF_8, priority = priority) {

    override fun autoReject(text: ByteArray): Boolean {
        return text.countSubArray(UTF8_INVALID_CHARACTER_BYTES) > 0
    }

    override fun autoAccept(text: ByteArray): Boolean {
        return text.startsWith(UTF8_BOM)
    }
}


fun ByteBuffer.toByteArray(): ByteArray {
    val bytesArray = ByteArray(this.remaining())
    this.get(bytesArray, 0, bytesArray.size)
    return bytesArray
}

fun Char.toCharBuffer(): CharBuffer {
    return CharBuffer.wrap(charArrayOf(this))
}

fun String.toCharsToBytesMap(charset: Charset): Map<Char, ByteArray> {
    return this.map { it to charset.encode(it.toCharBuffer()).toByteArray() }.toMap()
}

open class CharsetCharCounter(
    val charsToDetect: String = CZECH_CHARS + SLOVAK_CHARS,
    val charset: Charset,
    val charsToBytes: Map<Char, ByteArray> = charsToDetect.toCharsToBytesMap(charset),
    val priority: Int = 0
) {

    fun count(text: ByteArray): CharCountResult {
        if (autoReject(text)) {
            return CharCountResult(charset, 0.0)
        }
        if (autoAccept(text)) {
            return CharCountResult(charset, 100.0)
        }
        return CharCountResult(charset, (100.0 * foundCharacters(text) / text.size), priority = priority)
    }

    private fun foundCharacters(text: ByteArray): Int {
        val result = charsToBytes.values.map {
            val c: Int = text.countSubArray(it) * it.size
            c
        }.sum()
        return result
    }

    open fun autoAccept(text: ByteArray): Boolean {
        return false
    }

    open fun autoReject(text: ByteArray): Boolean {
        return false
    }
}

fun CharsetDetector.smartRead(
    inputStream: InputStream
): String {
    val rawData = inputStream.use { it.readBytes() }
    return String(rawData, detect(rawData).firstOrNull()?.charset ?: defaultCharset)
}

class CharsetDetector(
    val charCounters: Array<CharsetCharCounter>,
    val defaultCharset: Charset = UTF_8
) {

    fun detect(
        rawData: ByteArray
    ): List<CharCountResult> {
        return charCounters.map { it.count(rawData) }.sorted()
    }

    fun smartRead(
        rawData: ByteArray
    ): String {
        val charset = detect(rawData).firstOrNull()?.charset ?: defaultCharset
        return String(rawData, charset)
    }

    companion object {
        const val CHARS_TO_DETECT = CZECH_CHARS + SLOVAK_CHARS

        val CZECH_CHAR_COUNTERS = arrayOf(
            UTFCharsetCharCounter(CHARS_TO_DETECT),
            CharsetCharCounter(CHARS_TO_DETECT, charset = CP1250, priority = 5),
            CharsetCharCounter(CHARS_TO_DETECT, charset = ISO_8859_2, priority = 4),
            CharsetCharCounter(CHARS_TO_DETECT, charset = IBM852, priority = 3)
        )

        val CZECH_CHARSET_DETECTOR = CharsetDetector(
            charCounters = CZECH_CHAR_COUNTERS

        )
    }
}
