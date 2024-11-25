package com.grepp.quizy.quiz.domain.global.dto

data class Slice<T>(val content: List<T>, val hasNext: Boolean)
