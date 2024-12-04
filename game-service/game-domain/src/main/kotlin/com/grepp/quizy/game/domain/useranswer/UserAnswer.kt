package com.grepp.quizy.game.domain.useranswer

import com.grepp.quizy.game.domain.quiz.GameQuizAnswer

class UserAnswer(
    val userId: Long,
    val gameId: Long,
    val quizContent: String,
    val choice: String,
    val answer: String,
    val explanation: String,
    val isCorrect: Boolean
) {
    companion object {
        fun from(
            userId: Long,
            gameId: Long,
            quizContent: String,
            choice: String,
            answer: GameQuizAnswer,
            isCorrect: Boolean
        ) = UserAnswer(
            userId = userId,
            gameId = gameId,
            quizContent = quizContent,
            choice = choice,
            answer = answer.content,
            explanation = answer.explanation,
            isCorrect = isCorrect
        )
    }
}