package com.grepp.quizy.quiz.domain

import org.springframework.stereotype.Service

@Service
class QuizService(
        private val quizAppender: QuizAppender,
        private val quizTagManager: QuizTagManager,
) {
    fun createQuiz(
            content: QuizContent,
            tags: List<QuizTag>,
            options: List<QuizOption>,
            answer: QuizAnswer,
    ): Quiz {
        val preparedTags = quizTagManager.prepare(tags)
        return quizAppender.append(
                content,
                preparedTags,
                options,
                answer,
        )
    }
}
