package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.common.dto.Page

sealed interface SearchCondition {
    fun page(): Int

    fun size(): Int

    fun sort(): QuizSortType?
}

enum class QuizSortType {
    TRENDING,
    NEW,
}

data class UserSearchCondition(
        val field: String,
        private val page: Page,
        val sort: QuizSortType,
) : SearchCondition {
    override fun page() = page.page

    override fun size() = page.size

    override fun sort() = sort
}

data class GameQuizSearchCondition(
    val category: String,
    val difficulty: QuizDifficultyType,
    val size: Int,
) : SearchCondition {
    override fun page() = 0

    override fun size() = size

    override fun sort() = null
}

enum class QuizDifficultyType {
    EASY, MEDIUM, HARD, RANDOM
}
