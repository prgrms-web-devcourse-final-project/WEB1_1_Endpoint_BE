package com.grepp.quizy.matching.infra.redis.util

import java.nio.ByteBuffer
import java.nio.ByteOrder

fun FloatArray.toByteArray(): ByteArray {
    val buffer = ByteBuffer.allocate(this.size * 4)
    this.forEach { buffer.putFloat(it) }
    return buffer.array()
}

fun ByteArray.toFloatArray(): FloatArray {
    require(this.size % 4 == 0) { "ByteArray size must be a multiple of 4" }

    val floatArray = FloatArray(this.size / 4)
    val buffer = ByteBuffer.wrap(this)
    for (i in floatArray.indices) {
        floatArray[i] = buffer.float
    }
    return floatArray
}
