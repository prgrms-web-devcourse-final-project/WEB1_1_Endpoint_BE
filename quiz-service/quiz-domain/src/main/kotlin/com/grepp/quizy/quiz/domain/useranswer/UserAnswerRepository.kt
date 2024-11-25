package com.grepp.quizy.quiz.domain.useranswer

interface UserAnswerRepository {

    fun save(userAnswer: UserAnswer): UserAnswer

    fun findAllByUserAnswerId(userAnswerIds: List<UserAnswerId>): UserAnswerPackage
}
