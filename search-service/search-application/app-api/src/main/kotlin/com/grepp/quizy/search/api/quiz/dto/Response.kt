package com.grepp.quizy.search.api.quiz.dto

import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.quiz.GameQuizDetail
import com.grepp.quizy.search.domain.quiz.QuizWithDetail

data class SearchedQuizResponse(
    val quizzes: List<QuizWithDetail>,
    val hasNext: Boolean,
) {
    companion object {
        fun from(slice: Slice<QuizWithDetail>) =
                SearchedQuizResponse(slice.content, slice.hasNext)
    }
}

data class GameSetResponse(val quizzes: List<GameQuizDetail>)
