package com.grepp.quizy.quiz.domain

interface Answerable {

    fun getCorrectAnswer(): String

    fun getAnswerExplanation(): String

    fun isCorrect(userAnswer: String): Boolean =
            getCorrectAnswer() == userAnswer
}
