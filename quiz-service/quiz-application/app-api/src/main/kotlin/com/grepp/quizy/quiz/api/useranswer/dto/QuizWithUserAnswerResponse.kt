package com.grepp.quizy.quiz.api.useranswer.dto

import QuizResponse
import com.grepp.quizy.quiz.domain.useranswer.QuizWithUserAnswer

data class QuizWithUserAnswerResponse(
    val quiz: QuizResponse,
    val userAnswer: UserAnswerResponse
) {
    companion object {
        fun from(quizWithUserAnswer: QuizWithUserAnswer): QuizWithUserAnswerResponse {
            return QuizWithUserAnswerResponse(
                QuizResponse.from(quizWithUserAnswer.quiz),
                UserAnswerResponse.from(quizWithUserAnswer.userAnswer)
            )
        }
    }
}