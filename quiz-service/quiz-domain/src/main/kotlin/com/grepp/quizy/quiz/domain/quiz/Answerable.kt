package com.grepp.quizy.quiz.domain.quiz

interface Answerable {

    fun getQuizAnswer(): QuizAnswer

    fun getCorrectAnswer(): String = getQuizAnswer().value

    fun getAnswerExplanation(): String =
            getQuizAnswer().explanation

    fun updateAnswer(newAnswer: QuizAnswer): Answerable

    fun validateAnswer()

    fun isCorrect(userAnswer: String): Boolean =
            getCorrectAnswer() == userAnswer
}
