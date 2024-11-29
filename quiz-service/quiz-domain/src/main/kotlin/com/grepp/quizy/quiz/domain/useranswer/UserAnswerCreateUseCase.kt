package com.grepp.quizy.quiz.domain.useranswer

interface UserAnswerCreateUseCase {

    fun createUserAnswer(id: UserAnswerId, userChoice: Int): UserAnswer
}
