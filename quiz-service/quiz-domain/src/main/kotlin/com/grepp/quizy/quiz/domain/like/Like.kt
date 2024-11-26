package com.grepp.quizy.quiz.domain.like

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId

data class Like(val likerId: UserId, val quizId: QuizId) {}
