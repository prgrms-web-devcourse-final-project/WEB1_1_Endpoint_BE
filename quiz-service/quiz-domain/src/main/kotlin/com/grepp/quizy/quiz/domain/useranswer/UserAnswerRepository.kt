package com.grepp.quizy.quiz.domain.useranswer

interface UserAnswerRepository {

    fun save(userAnswer: UserAnswer): UserAnswer
}
