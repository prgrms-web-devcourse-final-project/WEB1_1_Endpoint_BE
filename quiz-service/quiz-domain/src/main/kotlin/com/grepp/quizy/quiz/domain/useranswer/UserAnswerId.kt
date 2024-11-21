package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.QuizId

data class UserAnswerId(val userId: UserId, val quizId: QuizId)
