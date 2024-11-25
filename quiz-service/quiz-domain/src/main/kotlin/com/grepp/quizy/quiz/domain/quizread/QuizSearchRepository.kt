package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.user.UserId

@JvmInline value class OptionNumber(val value: Int)

interface QuizSearchRepository {
    fun search(condition: UserSearchCondition): Slice<QuizForRead>

    fun search(condition: GameQuizSearchCondition): List<AnswerableQuiz>

    fun searchUserAnswer(
            userId: UserId,
            quizIds: List<QuizId>,
    ): UserAnswer
}

data class UserAnswer(val answers: Map<QuizId, OptionNumber>) {
    constructor() : this(emptyMap())

    fun getAnswerOf(id: QuizId) = answers[id]
}
