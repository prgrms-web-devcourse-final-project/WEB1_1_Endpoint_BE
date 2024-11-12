package com.grepp.quizy.common.dto

import java.util.function.Function

data class CursorResult<T>(val content: List<T>, val nextCursor: Long?) {
    fun <R> map(mapper: Function<T, R>?) =
            of(content.stream().map(mapper).toList(), nextCursor)

    companion object {
        fun <T> of(content: List<T>, nextCursor: Long?) =
                CursorResult(content, nextCursor)
    }
}
