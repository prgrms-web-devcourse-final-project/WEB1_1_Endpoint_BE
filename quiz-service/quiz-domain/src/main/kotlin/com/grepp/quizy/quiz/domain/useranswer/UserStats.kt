package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.user.UserId

data class UserStats(
    val userId: UserId,
    val correctRate: Double,
    val totalAnswerCount: Int,
    val achievement: Achievement
) {
}