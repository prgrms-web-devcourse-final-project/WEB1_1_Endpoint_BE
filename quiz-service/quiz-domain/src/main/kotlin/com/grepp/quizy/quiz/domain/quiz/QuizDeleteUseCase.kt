package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.useranswer.UserId

interface QuizDeleteUseCase {
    fun delete(id: QuizId, deleterId: UserId)
}
