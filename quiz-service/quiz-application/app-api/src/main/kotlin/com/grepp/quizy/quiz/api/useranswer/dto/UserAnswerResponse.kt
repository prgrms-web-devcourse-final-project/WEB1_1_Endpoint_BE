package com.grepp.quizy.quiz.api.useranswer.dto

import com.grepp.quizy.quiz.domain.useranswer.Choice
import com.grepp.quizy.quiz.domain.useranswer.UserAnswer
import java.time.LocalDateTime

data class UserAnswerResponse(
    val userId: Long,
    val quizId: Long,
    val choiceNumber: Int,
    val isCorrect: Boolean,
    val answeredAt: LocalDateTime
) {
    companion object {
        fun from(domain: UserAnswer): UserAnswerResponse {
            return UserAnswerResponse(
                userId = domain.key.userId.value,
                quizId = domain.key.quizId.value,
                choiceNumber = domain.choice.choiceNumber,
                isCorrect = (domain.choice as Choice.AnswerableChoice).isCorrect,
                answeredAt = domain.answeredAt!!
            )
        }
    }
}