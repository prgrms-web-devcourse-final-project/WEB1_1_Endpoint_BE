package com.grepp.quizy.common.dto

import java.util.function.Function

data class PageResult<T>(
        val content: List<T>,
        val totalPage: Int,
        val hasNext: Boolean,
) {
    fun <R> map(mapper: Function<T, R>?) =
            of(content.stream().map(mapper).toList(), totalPage, hasNext)

    companion object {
        fun <T> of(content: List<T>, totalPage: Int, hasNext: Boolean) =
                PageResult(content, totalPage, hasNext)
    }
}
