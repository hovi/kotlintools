package com.github.hovi.kotlintools.collection

val UTF_INVALID_CHARACTER_BYTES = byteArrayOf(-17, -65, -67)

fun ByteArray.countSubArray(subarray: ByteArray): Int {
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