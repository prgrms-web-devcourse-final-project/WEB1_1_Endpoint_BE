package com.grepp.quizy.quiz.domain.quiz

interface QuizMessageSender {

    fun send(message: QuizEvent)
}
