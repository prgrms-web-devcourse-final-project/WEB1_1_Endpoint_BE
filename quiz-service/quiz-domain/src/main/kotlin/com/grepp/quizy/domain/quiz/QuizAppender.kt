package com.grepp.quizy.domain.quiz

import org.springframework.stereotype.Component

@Component
class QuizAppender(
        private val quizRepository: QuizRepository,
        private val messagePublisher: QuizMessagePublisher,
) {
    fun append(title: String): Quiz {
        val appended = quizRepository.save(Quiz(0, title))
        messagePublisher.publish(QuizCreatedEvent.from(appended))
        return appended
    }
}
