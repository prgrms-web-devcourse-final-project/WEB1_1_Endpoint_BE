package com.grepp.quizy.game.infra.useranswer.entity

import com.grepp.quizy.game.domain.useranswer.UserAnswer

class UserAnswerRedis(
    val quizContent: String,
    val choice: String,
    val answer: String,
    val explanation: String,
    val isCorrect: Boolean
) {
    companion object {
        fun from(userAnswer: UserAnswer) = UserAnswerRedis(
            quizContent = userAnswer.quizContent,
            choice = userAnswer.choice,
            answer = userAnswer.answer,
            explanation = userAnswer.explanation,
            isCorrect = userAnswer.isCorrect
        )
    }

    fun toDomain(gameId: Long, userId: Long) = UserAnswer(
        quizContent = quizContent,
        choice = choice,
        answer = answer,
        explanation = explanation,
        isCorrect = isCorrect,
        gameId = gameId,
        userId = userId
    )
}