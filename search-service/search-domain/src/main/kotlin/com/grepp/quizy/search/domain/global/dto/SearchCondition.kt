package com.grepp.quizy.search.domain.global.dto

import com.grepp.quizy.common.dto.Page

data class SearchCondition(val field: String, private val page: Page, val sort: QuizSortType) {
    fun page() = page.page

    fun size() = page.size
}

enum class QuizSortType {
    TRENDING, NEW
}
