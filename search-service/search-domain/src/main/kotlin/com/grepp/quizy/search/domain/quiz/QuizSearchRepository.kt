package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.user.UserId

@JvmInline value class OptionNumber(val value: Int)

interface QuizSearchRepository {
    fun search(condition: SearchCondition): Slice<Quiz>

    fun searchUserAnswer(userId: UserId, quizIds: List<QuizId>): UserAnswer
}

data class UserAnswer(val answers: Map<QuizId, OptionNumber>) {
    constructor() : this(emptyMap())

    fun getAnswerOf(id: QuizId) = answers[id]
}
