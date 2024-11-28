package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.image.QuizImageId
import com.grepp.quizy.quiz.domain.user.UserId

interface QuizUpdateUseCase {
    fun update(
            id: QuizId,
            updatorId: UserId,
            updatedContent: QuizContent,
            updatedAnswer: QuizAnswer?,
            deleteImageIds: List<QuizImageId>
    ): Quiz
}
