package com.grepp.quizy.search.api.quiz.dto

import com.grepp.quizy.common.dto.Page
import com.grepp.quizy.search.domain.global.dto.QuizSortType
import com.grepp.quizy.search.domain.global.dto.SearchCondition

data class SearchParams(
    val keyword: String,
    val page: Int = 0,
    val size: Int = 0,
    val sort: QuizSortType = QuizSortType.TRENDING
) {
    fun SearchCondition() = SearchCondition(field = keyword, page = Page(page, size), sort = sort)
}
