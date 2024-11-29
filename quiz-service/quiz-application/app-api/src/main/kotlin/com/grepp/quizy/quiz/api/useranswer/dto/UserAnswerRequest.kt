package com.grepp.quizy.quiz.api.useranswer.dto

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerId
import com.grepp.quizy.quiz.domain.user.UserId

data class UserAnswerRequest(
    val quizId: Long,
    val choiceNumber: Int,
) {
    fun toId(userId: Long) = UserAnswerId(UserId(userId), QuizId(quizId))
}
