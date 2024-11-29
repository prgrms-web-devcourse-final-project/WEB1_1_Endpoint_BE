package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.like.Like

interface QuizCache {
    fun increaseLikeCount(quizId: QuizId)

    fun decreaseLikeCount(quizId: QuizId)

    fun cacheLike(like: Like)

    fun deleteLike(like: Like)

    fun isLikeCached(like: Like): Boolean?

    fun increaseSelectionCount(quizId: QuizId, optionNumber: Int)

    fun decreaseSelectionCount(quizId: QuizId, optionNumber: Int)
}
