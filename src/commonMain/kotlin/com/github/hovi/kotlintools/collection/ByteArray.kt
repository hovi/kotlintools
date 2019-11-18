package com.github.hovi.kotlintools.collection

val UTF8_INVALID_CHARACTER_BYTES = byteArrayOf(-17, -65, -67)

val UTF8_BOM = byteArrayOf(0xEF.toByte(), 0xBB.toByte(), 0xBF.toByte())

fun ByteArray.countSubArray(subarray: ByteArray): Int {
    if (subarray.isEmpty()) {
        return 0
    }
    var count = 0
    var found: Boolean
    this.forEachIndexed { index, byte ->
        found = true
        subarray.forEachIndexed { subindex, subbyte ->
            if (index + subindex >= size || subbyte != this[index + subindex]) {
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
    anotherArray.forEachIndexed { index, t ->
        if (this[index] != t) {
            return false
        }
    }
    return true
}