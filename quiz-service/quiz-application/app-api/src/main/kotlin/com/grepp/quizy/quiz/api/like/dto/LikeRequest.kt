package com.grepp.quizy.quiz.api.like.dto

import com.grepp.quizy.quiz.domain.like.Like
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserId

data class LikeRequest(val quizId: Long, val userId: Long) {

    fun toLike(): Like =
            Like(quizId = QuizId(quizId), likerId = UserId(userId))
}
