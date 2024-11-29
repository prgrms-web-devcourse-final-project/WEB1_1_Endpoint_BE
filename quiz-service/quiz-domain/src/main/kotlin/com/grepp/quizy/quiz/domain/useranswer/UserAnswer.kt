package com.grepp.quizy.quiz.domain.useranswer

data class UserAnswer(
    val id: UserAnswerId,
    val choice: Choice
) {
    companion object {
        fun create(
                userAnswerId: UserAnswerId,
                choice: Int,
                isCorrect: Boolean? = null,
        ): UserAnswer {
            return UserAnswer(
                    userAnswerId,
                    Choice.create(choice, isCorrect),
            )
        }
    }
}
