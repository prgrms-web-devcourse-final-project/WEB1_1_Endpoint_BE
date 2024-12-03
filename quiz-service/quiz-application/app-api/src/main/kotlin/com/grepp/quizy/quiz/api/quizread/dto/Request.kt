package com.grepp.quizy.quiz.api.quizread.dto

import com.grepp.quizy.common.dto.Page
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty
import com.grepp.quizy.quiz.domain.quizread.FeedSearchCondition
import com.grepp.quizy.quiz.domain.quizread.GameQuizSearchCondition
import com.grepp.quizy.quiz.domain.quizread.QuizSortType
import com.grepp.quizy.quiz.domain.quizread.UserSearchCondition

data class UserSearchParams(
        val keyword: List<String>,
        val page: Int = 0,
        val size: Int = 0,
        val sort: QuizSortType = QuizSortType.TRENDING,
) {
    fun UserSearchCondition() =
            UserSearchCondition(
                    fields = keyword,
                    page = Page(page, size),
                    sort = sort,
            )
}

data class FeedSearchParams(
        val category: QuizCategory?,
        val page: Int = 0,
        val size: Int = 0,
) {
        fun FeedSearchCondition() =
                FeedSearchCondition(
                        interest = category,
                        page = Page(page, size)
                )
}

data class GameQuizSearchParams(
        val category: String,
        val size: Int,
        val difficulty: QuizDifficulty
) {
        fun GameQuizSearchCondition() =
                GameQuizSearchCondition(
                        category = category,
                        size = size,
                        difficulty = difficulty
                )
}
