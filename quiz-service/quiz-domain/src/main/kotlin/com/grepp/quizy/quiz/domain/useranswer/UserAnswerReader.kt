package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.QuizId
import org.springframework.stereotype.Component

@Component
class UserAnswerReader(private val repository: UserAnswerRepository) {
    fun read(userId: UserId, quizIds: List<QuizId>) =
        repository.findAllByUserAnswerId(quizIds.map { UserAnswerId(userId, it) })
}