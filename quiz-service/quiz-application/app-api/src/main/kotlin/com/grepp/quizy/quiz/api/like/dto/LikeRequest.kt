package com.grepp.quizy.quiz.api.like.dto

import com.grepp.quizy.common.NoArg
import com.grepp.quizy.quiz.domain.like.Like
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId

@NoArg
data class LikeRequest(val quizId: Long) {

    fun toLike(userId: Long): Like =
            Like(quizId = QuizId(quizId), likerId = UserId(userId))
}
