package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.common.dto.Page
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty

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
    val fields: List<String>,
    private val page: Page,
    val sort: QuizSortType,
) : SearchCondition {
    override fun page() = page.page

    override fun size() = page.size

    override fun sort() = sort
}

data class GameQuizSearchCondition(
    val category: String,
    val difficulty: QuizDifficulty,
    val size: Int,
) : SearchCondition {
    override fun page() = 0

    override fun size() = size

    override fun sort() = null
}

data class FeedSearchCondition(
    val page: Page,
    val interest: QuizCategory?
) : SearchCondition {
    override fun page() = page.page

    override fun size() = page.size

    override fun sort() = QuizSortType.TRENDING
}