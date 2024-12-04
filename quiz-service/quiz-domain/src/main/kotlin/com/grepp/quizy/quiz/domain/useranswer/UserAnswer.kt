package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.Answerable
import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.useranswer.exception.UserAnswerException
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
            quiz: Quiz,
        ): UserAnswer {
            validateChoice(choice, quiz)
            val isCorrect = when (quiz) {
                is Answerable -> quiz.isCorrect(choice)
                else -> null
            }
            return UserAnswer(
                    userAnswerKey,
                    Choice.create(choice, isCorrect),
            )
        }

        private fun validateChoice(choice: Int, quiz: Quiz) {
            require(choice in 1..quiz.content.options.size) {
                throw UserAnswerException.InvalidUserChoice
            }
        }
    }
}
