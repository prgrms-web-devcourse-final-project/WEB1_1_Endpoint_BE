package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.QuizType

data class UserAnswer(val id: UserAnswerId, val choice: Choice) {
    companion object {
        fun createAnswerable(
                quizType: QuizType,
                userAnswerId: UserAnswerId,
                choice: String,
                isCorrect: Boolean,
        ): UserAnswer {
            return UserAnswer(
                    userAnswerId,
                    Choice.create(quizType, choice, isCorrect),
            )
        }

        fun createNonAnswerable(
                quizType: QuizType,
                userAnswerId: UserAnswerId,
                choice: String,
        ): UserAnswer {
            return UserAnswer(
                    userAnswerId,
                    Choice.create(quizType, choice),
            )
        }
    }
}
