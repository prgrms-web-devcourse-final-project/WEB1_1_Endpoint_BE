package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizUpdater(
    private val quizRepository: QuizRepository,
) {

    fun update(
            quiz: Quiz,
            userId: UserId,
            updatedContent: QuizContent,
            updatedAnswer: QuizAnswer?,
    ): Quiz {
        quiz.updateContent(userId, updatedContent)

        if (updatedAnswer != null && quiz is Answerable) {
            quiz.updateAnswer(updatedAnswer)
        }

        return quizRepository.save(quiz)
    }

    fun increaseCommentCount(quiz: Quiz) {
        quiz.increaseCommentCount()
        quizRepository.save(quiz)
    }

    fun decreaseCommentCount(quiz: Quiz) {
        quiz.decreaseCommentCount()
        quizRepository.save(quiz)
    }
}
