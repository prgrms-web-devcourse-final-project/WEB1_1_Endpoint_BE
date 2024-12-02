package com.grepp.quizy.quiz.api.useranswer.dto

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.useranswer.ReviewStatus
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerKey

data class UserAnswerReviewRequest(
    val quizId: Long,
    val reviewStatus: ReviewStatus
) {
    fun toUserAnswerKey(userId: Long): UserAnswerKey {
        return UserAnswerKey(UserId(userId), QuizId(quizId))
    }
}
