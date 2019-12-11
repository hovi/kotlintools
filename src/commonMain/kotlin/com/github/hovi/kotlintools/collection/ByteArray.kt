package com.github.hovi.kotlintools.collection

val UTF8_INVALID_CHARACTER_BYTES = byteArrayOf(-17, -65, -67)

val UTF8_BOM = byteArrayOf(0xEF.toByte(), 0xBB.toByte(), 0xBF.toByte())
val UTF8_REPLACEMENT = byteArrayOf(0xef.toByte(), 0xbf.toByte(), 0xbd.toByte())

fun ByteArray.countSubArray(subarray: ByteArray): Int {
    if (subarray.isEmpty()) {
        return 0
    }
    var count = 0
    var found: Boolean
    this.forEachIndexed { index, byte ->
        found = true
        subarray.forEachIndexed { subIndex, subByte ->
            if (index + subIndex >= size || subByte != this[index + subIndex]) {
                found = false
            }
        }
        if (found) {
            count += 1
        }
    }
    return count
}

fun ByteArray.startsWith(anotherArray: ByteArray): Boolean {
    if (anotherArray.size > this.size) {
        return false
    }
    anotherArray.forEachIndexed { index, byte ->
        if (this[index] != byte) {
            return false
        }
    }
    return true
}