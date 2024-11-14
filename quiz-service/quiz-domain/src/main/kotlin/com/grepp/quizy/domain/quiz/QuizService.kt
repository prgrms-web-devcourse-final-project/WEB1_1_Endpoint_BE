package com.grepp.quizy.domain.quiz

import org.springframework.stereotype.Service

@Service
class QuizService(private val quizAppender: QuizAppender) {
    fun createQuiz(title: String): Quiz {
        return quizAppender.append(title)
    }
}
