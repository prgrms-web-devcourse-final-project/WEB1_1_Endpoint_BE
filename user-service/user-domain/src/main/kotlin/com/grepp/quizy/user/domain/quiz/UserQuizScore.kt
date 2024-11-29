package com.grepp.quizy.user.domain.quiz

data class UserQuizScore(
    val solvedProblems: Int = 0,
    val correctAnswerRate: Double = 0.0,
    val achievements: List<String> = emptyList()
)
