package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.global.dto.SearchCondition
import com.grepp.quizy.search.domain.global.dto.Slice

@JvmInline value class OptionNumber(val value: Int)

interface QuizSearchRepository {
    fun search(condition: SearchCondition): Slice<Quiz>

    fun searchUserAnswer(quizIds: List<QuizId>): UserAnswer
}

data class UserAnswer(val answers: Map<QuizId, OptionNumber>) {
    fun getAnswerOf(id: QuizId) = answers[id]
}
