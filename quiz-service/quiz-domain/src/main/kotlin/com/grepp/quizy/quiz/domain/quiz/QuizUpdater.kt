package com.grepp.quizy.quiz.domain.quiz

import org.springframework.stereotype.Component

@Component
class QuizUpdater(
        private val quizRepository: QuizRepository
) {

    fun update(
            quiz: Quiz,
            updatedContent: QuizContent,
            updatedAnswer: QuizAnswer?,
    ): Quiz {
        quiz.updateContent(updatedContent)

        if (updatedAnswer != null && quiz is Answerable) {
            quiz.updateAnswer(updatedAnswer)
        }

        return quizRepository.update(quiz)
    }
}
