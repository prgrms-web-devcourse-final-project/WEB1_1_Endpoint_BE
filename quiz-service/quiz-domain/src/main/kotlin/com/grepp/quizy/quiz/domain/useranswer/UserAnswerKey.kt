package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId

data class UserAnswerKey(val userId: UserId, val quizId: QuizId)
