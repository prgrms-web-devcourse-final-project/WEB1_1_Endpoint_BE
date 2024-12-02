package com.grepp.quizy.quiz.api.useranswer.dto

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserAnswerKey
import com.grepp.quizy.quiz.domain.user.UserId

data class UserAnswerRequest(
    val quizId: Long,
    val choiceNumber: Int,
) {
    fun toId(userId: Long) = UserAnswerKey(UserId(userId), QuizId(quizId))
}
