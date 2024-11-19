package com.grepp.quizy.quiz.domain.useranswer

interface UserAnswerCreateUseCase {

    fun create(
            id: UserAnswerId,
            userChoice: String,
    ): UserAnswer
}
