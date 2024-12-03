package com.grepp.quizy.common.dto

import java.util.function.Function
import java.util.stream.Collectors

data class SliceResult<T>(val content: List<T>, val hasNext: Boolean) {
    fun <R> map(mapper: Function<T, R>): SliceResult<R> {
        val mappedContent = content.stream().map(mapper).collect(Collectors.toList())
        return SliceResult(mappedContent, hasNext)
    }

    companion object {
        fun <T> of(content: List<T>, hasNext: Boolean): SliceResult<T> {
            return SliceResult(content, hasNext)
        }
    }
}