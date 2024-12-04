package com.grepp.quizy.user.domain.quiz

data class UserQuizScore(
    val userId: Long = 0,
    val achievements: List<Achievement> = emptyList(),
    val totalAnswered: Int = 0,
    val correctRate: Double = 0.0
)
