package com.github.hovi.kotlintools.collection

import kotlin.test.*


class ByteArrayTest {

    @Test
    fun testStartsWith() {
        assertTrue(byteArrayOf(1, 2, 3).startsWith(byteArrayOf(1, 2, 3)))
        assertTrue(byteArrayOf(1, 2, 3).startsWith(byteArrayOf(1, 2)))
        assertTrue(byteArrayOf(1, 2, 3).startsWith(byteArrayOf(1)))
        assertFalse(byteArrayOf(1, 2, 3).startsWith(byteArrayOf(4)))
        assertFalse(byteArrayOf(1, 2, 3).startsWith(byteArrayOf(4, 1)))
    }



    @Test
    fun testSubarraySingle() {
        assertEquals(1, byteArrayOf(1, 2, 3).countSubArray(byteArrayOf(1, 2, 3)))
        assertEquals(1, byteArrayOf(1, 2, 3).countSubArray(byteArrayOf(1, 2)))
        assertEquals(3, byteArrayOf(1, 1, 1).countSubArray(byteArrayOf(1)))
    }
}