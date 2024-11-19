package com.grepp.quizy.quiz.api.useranswer.dto

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerId
import com.grepp.quizy.quiz.domain.useranswer.UserId

data class UserAnswerRequest(
        val userId: Long,
        val quizId: Long,
        val choice: String,
) {
    fun toId() = UserAnswerId(UserId(userId), QuizId(quizId))
}
