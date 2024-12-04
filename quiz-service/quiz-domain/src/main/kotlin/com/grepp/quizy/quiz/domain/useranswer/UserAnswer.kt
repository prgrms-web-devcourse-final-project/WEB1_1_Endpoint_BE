package com.grepp.quizy.quiz.domain.useranswer

import java.time.LocalDateTime

data class UserAnswer(
    val key: UserAnswerKey,
    val choice: Choice,
    private var _reviewStatus: ReviewStatus = ReviewStatus.NOT_REVIEWED,
    val answeredAt: LocalDateTime = LocalDateTime.now(),
) {

    val reviewStatus: ReviewStatus
        get() = _reviewStatus

    fun review(reviewStatus: ReviewStatus) {
        this._reviewStatus = reviewStatus
    }

    fun isCorrect(): Boolean {
        return (choice as Choice.AnswerableChoice).isCorrect
    }

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
