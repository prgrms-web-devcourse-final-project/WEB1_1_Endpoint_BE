package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.QuizId

data class UserAnswerPackage(val answers: Map<QuizId, Choice>) {
    constructor() : this(emptyMap())

    fun getChoiceOf(id: QuizId) = answers[id]
}