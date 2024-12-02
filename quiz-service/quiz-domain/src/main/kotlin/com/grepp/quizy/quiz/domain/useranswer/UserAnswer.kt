package com.grepp.quizy.quiz.domain.useranswer

import java.time.LocalDateTime

data class UserAnswer(
    val key: UserAnswerKey,
    val choice: Choice,
    val answeredAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            userAnswerKey: UserAnswerKey,
            choice: Int,
            isCorrect: Boolean? = null,
        ): UserAnswer {
            return UserAnswer(
                    userAnswerKey,
                    Choice.create(choice, isCorrect),
            )
        }
    }
}
