package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.user.UserId

interface QuizCreateUseCase {
    fun create(
            creatorId: UserId,
            type: QuizType,
            content: QuizContent,
            answer: QuizAnswer,
    ): Quiz
}
