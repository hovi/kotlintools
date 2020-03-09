package com.github.hovi.kotlintools.datetime

expect val timestamp: Long

class TimeMeasure(var startTime: Long = timestamp) {
    fun restart() {
        startTime = timestamp
    }

    fun end(): Long {
        return timestamp - startTime
    }

    fun isRunningLongerThan(milliseconds: Long): Boolean {
        return end() > milliseconds
    }
}


public inline fun measureTimeMillis(block: () -> Unit): Long {
    val start = timestamp
    block()
    return timestamp - start
}