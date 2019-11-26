package com.github.hovi.kotlintools.io

actual fun printErr(message: Any?) {
    System.err.print(message)
}

actual fun printlnErr(message: Any?) {
    System.err.println(message)
}