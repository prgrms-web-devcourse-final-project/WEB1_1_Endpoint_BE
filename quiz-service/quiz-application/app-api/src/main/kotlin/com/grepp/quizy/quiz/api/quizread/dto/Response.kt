package com.grepp.quizy.quiz.api.quizread.dto

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.quizread.GameQuizDetail
import com.grepp.quizy.quiz.domain.quizread.QuizFeed
import com.grepp.quizy.quiz.domain.quizread.QuizWithDetail

data class QuizSliceResponse(
    val quizzes: List<QuizWithDetail>,
    val hasNext: Boolean,
) {
    companion object {
        fun from(slice: Slice<QuizWithDetail>) =
                QuizSliceResponse(slice.content, slice.hasNext)
    }
}

data class QuizFeedResponse(
    val category: QuizCategory,
    val content: QuizSliceResponse
) {
    companion object {
        fun from(feed: QuizFeed) =
            QuizFeedResponse(feed.category, QuizSliceResponse.from(feed.slice))
    }
}

data class GameSetResponse(val quizzes: List<GameQuizDetail>)
