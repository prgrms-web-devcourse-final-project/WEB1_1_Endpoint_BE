package com.grepp.quizy.user.domain.quiz

data class UserQuizScore(
    val solvedProblems: Int,
    val correctAnswerRate: Double,
    val achievements: List<String>
)
