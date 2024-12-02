package com.grepp.quizy.quiz.domain.useranswer

interface UserAnswerUpdateUseCase {
    fun reviewUserAnswer(key: UserAnswerKey, reviewStatus: ReviewStatus)
}