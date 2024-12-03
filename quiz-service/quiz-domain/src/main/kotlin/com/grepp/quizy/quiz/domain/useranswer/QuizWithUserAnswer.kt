package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.Quiz

data class QuizWithUserAnswer(
    val quiz: Quiz,
    val userAnswer: UserAnswer
) {
    companion object {
        fun of(quiz: Quiz, userAnswer: UserAnswer): QuizWithUserAnswer {
            return QuizWithUserAnswer(quiz, userAnswer)
        }
    }
}