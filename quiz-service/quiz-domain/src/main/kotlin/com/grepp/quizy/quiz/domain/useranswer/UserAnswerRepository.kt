package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId

interface UserAnswerRepository {

    fun save(userAnswer: UserAnswer): UserAnswer

    fun findAllByUserAnswerId(userAnswerIds: List<UserAnswerId>): UserAnswerPackage

    fun findAllByUserId(userId: UserId): List<QuizId>
}
