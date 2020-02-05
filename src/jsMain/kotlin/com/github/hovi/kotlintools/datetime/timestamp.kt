package com.github.hovi.kotlintools.datetime

import kotlin.js.Date
import kotlin.math.round

actual val timestamp: Long
    get() = round(Date().getTime()).toLong()