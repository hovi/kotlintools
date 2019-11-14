package eu.karelhovorka.tools.string

import kotlin.test.Test
import kotlin.test.*


class StringUtilTest {
    @Test
    fun removeCzechAccents() {
        assertEquals("prilis zlutoucky kun upel dabelske ody", "příliš žluťoučký kůň úpěl ďábelské ódy".removeAccents())
        assertEquals(
            "prilis zlutoucky kun upel dabelske odyprilis zlutoucky kun upel dabelske ody",
            "příliš žluťoučký kůň úpěl ďábelské ódypříliš žluťoučký kůň úpěl ďábelské ódy".removeAccents()
        )
    }

    @Test
    fun removeAccent() {
        assertEquals('a', 'á'.removeAccent())
        assertEquals('e', 'ě'.removeAccent())
    }

    @Test
    fun vowel() {
        assertTrue('e'.isVowel())
        assertTrue('á'.isVowel())
        assertTrue('ý'.isVowel())
    }

    @Test
    fun createId() {
        assertEquals("slunecni-hrob", "Sluneční hrob".createId())
    }


}