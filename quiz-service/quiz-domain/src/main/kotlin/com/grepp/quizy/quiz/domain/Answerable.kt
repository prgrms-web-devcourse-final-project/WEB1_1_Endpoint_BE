package com.grepp.quizy.quiz.domain

interface Answerable {

    fun getAnswer(): QuizAnswer

    fun getCorrectAnswer(): String

    fun getAnswerExplanation(): String

    fun isCorrect(userAnswer: String): Boolean =
            getCorrectAnswer() == userAnswer
}
