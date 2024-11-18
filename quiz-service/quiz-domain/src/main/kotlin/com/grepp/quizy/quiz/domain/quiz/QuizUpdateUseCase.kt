package com.grepp.quizy.quiz.domain.quiz

interface QuizUpdateUseCase {
    fun update(
            id: QuizId,
            updatedContent: QuizContent,
            updatedAnswer: QuizAnswer?,
    ): Quiz
}
