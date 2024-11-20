package com.grepp.quizy.search.domain.global.dto

data class Slice<T>(val content: List<T>, val hasNext: Boolean)
