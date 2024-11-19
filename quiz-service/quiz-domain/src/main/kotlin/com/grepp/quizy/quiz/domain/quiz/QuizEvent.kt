package com.grepp.quizy.quiz.domain.quiz

import java.io.Serializable

interface QuizEvent : Serializable {
    fun getQuizId(): Long
}
