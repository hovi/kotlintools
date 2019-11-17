package com.github.hovi.kotlintools.charset


import com.github.hovi.kotlintools.charset.CzechCharsetDetector.smartRead
import com.github.hovi.kotlintools.string.isIncorrectlyEncoded
import org.junit.Assert
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.text.Charsets.UTF_8

var CZECH_TEXT_LOWER = "příliš žluťoučký kůň úpěl ďábelské ódy"

var CZECH_TEXT_UPPER = "PŘÍLIŠ ŽLUŤOUČKÝ KŮŇ ÚPĚL ĎÁBELSKÉ ÓDY"


class CzechCharsetDetectorTest {
    private val cp: ByteArray = CZECH_TEXT_LOWER.toByteArray(CP1250)
    private val iso: ByteArray = CZECH_TEXT_LOWER.toByteArray(ISO_8859_2)
    private val utf: ByteArray = CZECH_TEXT_LOWER.toByteArray(UTF_8)

    @Test
    fun testGuessEncoding() {
        testText(CZECH_TEXT_LOWER)
        testText(CZECH_TEXT_UPPER)
        testText("řeřicha")
    }

    @Test
    fun testSingleCharacterLower() {
        for (i in CZECH_TEXT_LOWER.indices) {
            testText(CZECH_TEXT_LOWER.substring(i, i + 1))
        }
    }

    @Test
    fun testSingleCharacterUpper() {
        for (i in CZECH_TEXT_UPPER.indices) {
            testText(CZECH_TEXT_UPPER.substring(i, i + 1))
        }
    }

    @Test
    fun testSingleCharacterSk() {
        val text: String = "ÄäÔôĹĺľĽ"
        for (i in 0 until text.length) {
            testText(text.substring(i, i + 1))
        }
    }

    @Test
    fun testIsIncorrect() {
        Assert.assertTrue(String(cp, UTF_8).isIncorrectlyEncoded())
        Assert.assertTrue(String(iso, UTF_8).isIncorrectlyEncoded())
        Assert.assertTrue(String(utf, CP1250).isIncorrectlyEncoded())
        //those actually work and don't contain invalid characters, just aren't czech:
        Assert.assertFalse(String(utf, ISO_8859_2).isIncorrectlyEncoded())
        Assert.assertFalse(String(cp, ISO_8859_2).isIncorrectlyEncoded())
        Assert.assertFalse(String(iso, CP1250).isIncorrectlyEncoded())
    }

    @Test
    fun testFiles() {
        val charsets = presetDetectors.map { it.charset }
        charsets.forEach {
            val file = File("src/jvmTest/resources/txt/charset_${it.name()}.txt")
            val bytes = file.readBytes()
            assertEquals(it, CzechCharsetDetector.guessCharset(bytes).first().charset)
        }
    }

    private fun testText(text: String) {
        if ("ľ" == text || "Ľ" == text) {
            return
        }
        assertEquals(text, smartRead(text.toByteArray(CP1250)))
        assertEquals(text, smartRead(text.toByteArray(ISO_8859_2)))
        assertEquals(text, smartRead(text.toByteArray(UTF_8)))
        val lower = text.toLowerCase()
        assertEquals(lower, smartRead(lower.toByteArray(CP1250)))
        assertEquals(lower, smartRead(lower.toByteArray(ISO_8859_2)))
        assertEquals(lower, smartRead(lower.toByteArray(UTF_8)))
        val upper = text.toUpperCase()
        assertEquals(upper, smartRead(upper.toByteArray(CP1250)))
        assertEquals(upper, smartRead(upper.toByteArray(ISO_8859_2)))
        assertEquals(upper, smartRead(upper.toByteArray(UTF_8)))
    }
}
