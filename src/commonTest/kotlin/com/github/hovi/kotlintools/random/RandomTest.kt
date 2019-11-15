package com.github.hovi.kotlintools.random

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue


class RandomTest {
    val seed = 0

    @Test
    fun randomString() {
        repeat(50) {
            val size = 10
            assertStringMatchesRegex(Random(seed = seed).nextString(size), ("[A-Z0-9]{${size}}".toRegex()))
        }
    }

    fun assertStringMatchesRegex(text: String, regex: Regex) {
        assertTrue(text.matches(regex))
    }

}