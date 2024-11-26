package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.user.UserId

interface QuizDeleteUseCase {
    fun delete(id: QuizId, deleterId: UserId)
}
