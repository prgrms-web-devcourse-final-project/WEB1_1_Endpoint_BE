package com.grepp.quizy.domain.quiz

import java.io.Serializable

data class QuizCreatedEvent(val id: Long = 0, val title: String = "") :
        Serializable {

    companion object {
        fun from(quiz: Quiz): QuizCreatedEvent {
            return QuizCreatedEvent(quiz.id, quiz.title)
        }
    }
}
