package com.grepp.quizy.quiz.api.quizread.dto

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.quizread.GameQuizDetail
import com.grepp.quizy.quiz.domain.quizread.QuizWithDetail

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
