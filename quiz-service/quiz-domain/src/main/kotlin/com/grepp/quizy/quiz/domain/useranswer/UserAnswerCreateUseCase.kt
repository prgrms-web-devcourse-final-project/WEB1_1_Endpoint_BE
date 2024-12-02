package com.grepp.quizy.quiz.domain.useranswer

interface UserAnswerCreateUseCase {

    fun createUserAnswer(key: UserAnswerKey, userChoice: Int): UserAnswer
}
