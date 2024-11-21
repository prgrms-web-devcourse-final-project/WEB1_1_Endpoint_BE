package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.useranswer.UserId

interface QuizUpdateUseCase {
    fun update(
            id: QuizId,
            updatorId: UserId,
            updatedContent: QuizContent,
            updatedAnswer: QuizAnswer?,
    ): Quiz

    fun toggleLike(id: QuizId, userId: UserId)
}
