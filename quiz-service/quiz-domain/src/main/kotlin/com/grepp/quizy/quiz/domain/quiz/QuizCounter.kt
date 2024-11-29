package com.grepp.quizy.quiz.domain.quiz

import org.springframework.stereotype.Component

@Component
class QuizCounter(
    private val quizCache: QuizCache
) {
    fun increaseSelectionCount(quizId: QuizId, optionNumber: Int) {
        quizCache.increaseSelectionCount(quizId, optionNumber)
    }

    fun decreaseSelectionCount(quizId: QuizId, optionNumber: Int) {
        quizCache.decreaseSelectionCount(quizId, optionNumber)
    }

    fun increaseLikeCount(quizId: QuizId) {
        quizCache.increaseLikeCount(quizId)
    }

    fun decreaseLikeCount(quizId: QuizId) {
        quizCache.decreaseLikeCount(quizId)
    }
}