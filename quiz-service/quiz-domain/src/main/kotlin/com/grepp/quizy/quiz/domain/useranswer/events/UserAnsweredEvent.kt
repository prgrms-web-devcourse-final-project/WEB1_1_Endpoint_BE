package com.grepp.quizy.quiz.domain.useranswer.events

import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.useranswer.UserAnswer
import java.time.LocalDateTime

data class UserAnsweredEvent(
    val userId: UserId,
    val quizId: QuizId,
    val category: QuizCategory,
    val difficulty: QuizDifficulty,
    val isCorrect: Boolean,
    val answeredAt: LocalDateTime
) {
    companion object {
        fun from(quiz: Quiz, userAnswer: UserAnswer): UserAnsweredEvent {
            require(quiz is Answerable) {
                "UserAnsweredEvent can only be created from Answerable Quiz"
            }

            return UserAnsweredEvent(
                userId = userAnswer.key.userId,
                quizId = userAnswer.key.quizId,
                category = quiz.content.category,
                difficulty = quiz.getDifficulty(),
                isCorrect = userAnswer.isCorrect(),
                answeredAt = userAnswer.answeredAt
            )
        }
    }
}