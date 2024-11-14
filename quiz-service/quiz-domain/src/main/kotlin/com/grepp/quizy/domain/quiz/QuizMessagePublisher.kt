package com.grepp.quizy.domain.quiz

interface QuizMessagePublisher {

    fun publish(quizCreatedEvent: QuizCreatedEvent)
}
