package com.grepp.quizy.user.domain.quiz

interface QuizClient {
    fun getUserQuizScore(userId: Long): UserQuizScore
}