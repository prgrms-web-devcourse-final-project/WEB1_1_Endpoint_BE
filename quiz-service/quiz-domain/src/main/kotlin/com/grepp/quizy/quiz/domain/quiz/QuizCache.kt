package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.like.Like

interface QuizCache {
    fun increaseLikeCount(quizId: QuizId): Long

    fun decreaseLikeCount(quizId: QuizId): Long

    fun cacheLike(like: Like)

    fun deleteLike(like: Like)

    fun isLikeCached(like: Like): Boolean?
}
