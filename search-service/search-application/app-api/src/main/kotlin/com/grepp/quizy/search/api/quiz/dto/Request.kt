package com.grepp.quizy.search.api.quiz.dto

import com.grepp.quizy.common.dto.Page
import com.grepp.quizy.search.domain.quiz.GameQuizSearchCondition
import com.grepp.quizy.search.domain.quiz.QuizDifficultyType
import com.grepp.quizy.search.domain.quiz.QuizSortType
import com.grepp.quizy.search.domain.quiz.UserSearchCondition

data class UserSearchParams(
        val keyword: String,
        val page: Int = 0,
        val size: Int = 0,
        val sort: QuizSortType = QuizSortType.TRENDING,
) {
    fun UserSearchCondition() =
            UserSearchCondition(
                    field = keyword,
                    page = Page(page, size),
                    sort = sort,
            )
}

data class GameQuizSearchParams(
        val category: String,
        val size: Int,
        val difficulty: QuizDifficultyType
) {
        fun GameQuizSearchCondition() =
                GameQuizSearchCondition(
                        category = category,
                        size = size,
                        difficulty = difficulty
                )
}
