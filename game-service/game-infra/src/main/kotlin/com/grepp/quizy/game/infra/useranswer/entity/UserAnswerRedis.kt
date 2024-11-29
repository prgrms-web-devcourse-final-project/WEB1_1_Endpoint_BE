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
}