package com.github.hovi.kotlintools.exception

import kotlin.reflect.KClass


var globalLogger: (Throwable) -> Unit = {}

inline fun <reified E : Throwable, T : Any?> swallowIfException(
    noinline log: (E) -> Unit = globalLogger,
    block: () -> T?
): T? {
    try {
        return block()
    } catch (e: Throwable) {
        if (e is E) {
            log(e)
            return null
        }
        throw e
    }
}

fun <T : Any?> KClass<out Throwable>.swallow(
    log: (Throwable) -> Unit = globalLogger,
    block: () -> T?
): T? {
    try {
        return block()
    } catch (e: Throwable) {
        //if (e::class.isSubclassOf(this)) {
        if (e::class == this) {
            log(e)
            return null
        }
        throw e
    }
}

inline fun <T : Any?> swallow(
    noinline log: (Throwable) -> Unit = globalLogger,
    block: () -> T?
): T? {
    try {
        return block()
    } catch (e: Throwable) {
        log(e)
        return null
    }
}
