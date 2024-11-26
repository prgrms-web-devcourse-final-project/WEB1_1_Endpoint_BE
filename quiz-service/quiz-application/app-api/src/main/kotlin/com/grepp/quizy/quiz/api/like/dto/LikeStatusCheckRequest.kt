package com.grepp.quizy.quiz.api.like.dto

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId

data class LikeStatusCheckRequest(
        val userId: Long,
        val quizIds: List<Long>,
) {
    fun toUserId() = UserId(userId)

    fun toQuizIds() = quizIds.map { QuizId(it) }
}
