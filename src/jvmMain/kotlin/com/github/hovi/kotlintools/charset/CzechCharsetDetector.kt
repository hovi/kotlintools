package com.github.hovi.kotlintools.charset


import com.github.hovi.kotlintools.collection.UTF_INVALID_CHARACTER_BYTES
import com.github.hovi.kotlintools.collection.countSubArray
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8


val CP1250: Charset = Charset.forName("CP1250")

val ISO_8859_2: Charset = Charset.forName("ISO-8859-2")


fun ByteBuffer.toByteArray(): ByteArray {
    val bytesArray = ByteArray(this.remaining())
    this.get(bytesArray, 0, bytesArray.size)
    return bytesArray
}

fun Char.toCharBuffer(): CharBuffer {
    return CharBuffer.wrap(charArrayOf(this))
}

const val CZECH_CHARS_LOWER = "říšžťčýůňúěďáéó"

const val CZECH_CHARS_UPPER = "ŘÍŠŽŤČÝŮŇÚĚĎÁÉÓ"

const val CZECH_CHARS = CZECH_CHARS_LOWER + CZECH_CHARS_UPPER

const val SLOVAK_CHARS = "ÄäÔôĹĽĺ" //'ľ' causes problems - has same as 'ž' in  other encoding


class CharsetLetters(val charset: Charset, text: String = CZECH_CHARS + SLOVAK_CHARS) {

    val charsToBytes: Map<Char, ByteArray> = text.map { it to charset.encode(it.toCharBuffer()).toByteArray() }.toMap()
}

data class DetectionResult(val charset: Charset, val score: Double, val priority: Int = 0)


enum class ResultComparator : Comparator<DetectionResult> {
    INSTANCE;

    override fun compare(o1: DetectionResult, o2: DetectionResult): Int {
        return -if (o1.score == o2.score) {
            o1.priority.compareTo(o2.priority)
        } else {
            o1.score.compareTo(o2.score)
        }
    }

}

class UTFCharsetDetector() : CharsetDetector(UTF_8, priority = 10) {

    override fun autoreject(text: ByteArray): Boolean {
        return text.countSubArray(UTF_INVALID_CHARACTER_BYTES) > 0
    }
}

open class CharsetDetector(val charset: Charset, val priority: Int = 0) {

    val charsetLetters = CharsetLetters(charset)

    fun detect(text: ByteArray): DetectionResult {
        if (autoreject(text)) {
            return DetectionResult(charset, 0.0)
        }
        if (autoaccept(text)) {
            return DetectionResult(charset, 100.0)
        }
        return DetectionResult(charset, (100.0 * foundCharacters(text) / text.size), priority = priority)
    }

    fun foundCharacters(text: ByteArray): Int {
        val result = charsetLetters.charsToBytes.values.map {
            val c: Int = text.countSubArray(it) * it.size
            c
        }.sum()
        return result
    }

    open fun autoaccept(text: ByteArray): Boolean {
        return false
    }

    open fun autoreject(text: ByteArray): Boolean {
        return false
    }
}

object CzechCharsetDetector {

    fun guessCharset(
        rawData: ByteArray, detectors: Array<CharsetDetector> = arrayOf(
            CharsetDetector(CP1250, 5),
            CharsetDetector(ISO_8859_2),
            UTFCharsetDetector()
        )
    ): List<DetectionResult> {
        return detectors.map { it.detect(rawData) }.sortedWith(ResultComparator.INSTANCE)
    }

    fun smartRead(inputStream: InputStream, default: Charset = UTF_8): String {
        val rawData = inputStream.use { it.readBytes() }
        return String(rawData, guessCharset(rawData).firstOrNull()?.charset ?: default)
    }

    fun smartRead(rawData: ByteArray, default: Charset = UTF_8): String {
        val charset = guessCharset(rawData).firstOrNull()?.charset ?: default
        return String(rawData, charset)
    }
}
