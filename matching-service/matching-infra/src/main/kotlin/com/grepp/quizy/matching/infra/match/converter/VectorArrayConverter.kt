package com.grepp.quizy.matching.infra.match.converter

import java.nio.ByteBuffer

fun FloatArray.toByteArray(): ByteArray {
    val buffer = ByteBuffer.allocate(this.size * 4)
    this.forEach { buffer.putFloat(it) }
    return buffer.array()
}

fun ByteArray.toFloatArray(): FloatArray {
    val buffer = ByteBuffer.wrap(this)
    return FloatArray(this.size / 4) { buffer.getFloat() }
}
