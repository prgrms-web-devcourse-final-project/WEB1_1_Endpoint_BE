package com.grepp.quizy.quiz.domain.user

interface QuizUserCache {
    fun cache(quizUser: QuizUser): QuizUser
    fun getById(userId: UserId): QuizUser?
    fun evict(id: UserId)
}