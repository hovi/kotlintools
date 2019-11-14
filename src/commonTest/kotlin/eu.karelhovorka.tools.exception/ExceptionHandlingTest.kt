package eu.karelhovorka.tools.exception

import kotlin.test.*


class ExceptionHandlingTest {
    val throwing = "throwing"

    @Test
    fun testSwallow() {
        val result = swallow(log = {
            assertEquals(it.message, throwing)
        }) {
            error(throwing)
        }
        assertEquals(null, result)
    }

    @Test
    fun testReturnValue() {
        val x = swallow {
            2 + 2
        }
        assertEquals(2 + 2, x)
    }

    @Test
    fun testReturnValueSpecificException() {
        val x = IllegalStateException::class.swallow() {
            2 + 2
        }
        assertEquals(2 + 2, x)
    }

    @Test
    fun testMatchingException() {
        assertFailsWith(
            RuntimeException::class
        ) {
            RuntimeException::class.swallow() {
                throw IllegalStateException(throwing)
            }

        }
    }

    @Test
    fun testReturnValueZ() {
        val x = IllegalStateException::class.swallow() {
            throw IllegalStateException(throwing)
        }
        assertEquals(null, x)
    }
}