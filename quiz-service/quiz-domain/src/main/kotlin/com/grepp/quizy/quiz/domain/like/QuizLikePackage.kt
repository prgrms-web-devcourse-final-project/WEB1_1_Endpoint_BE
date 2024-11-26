package com.grepp.quizy.quiz.domain.like

import com.grepp.quizy.quiz.domain.quiz.QuizId

data class QuizLikePackage(val status: Map<QuizId, Boolean>) {
    constructor() : this(emptyMap())

    fun isLikedBy(quizId: QuizId) = status[quizId] ?: false
}
