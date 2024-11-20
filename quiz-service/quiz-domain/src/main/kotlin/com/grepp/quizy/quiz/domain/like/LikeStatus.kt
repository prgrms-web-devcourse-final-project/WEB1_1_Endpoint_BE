package com.grepp.quizy.quiz.domain.like

import com.grepp.quizy.quiz.domain.quiz.QuizId

data class LikeStatus(val quizId: QuizId, val isLiked: Boolean) {}
