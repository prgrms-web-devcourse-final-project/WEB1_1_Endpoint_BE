package com.grepp.quizy.quiz.domain.like

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.stereotype.Service

@Service
class LikeService(private val likeManager: LikeManager) {

    fun toggleLike(like: Like): Boolean {
        return likeManager.toggleLike(like)
    }

    fun isLikedIn(
            userId: UserId,
            quizIds: List<QuizId>,
    ): List<LikeStatus> {
        return likeManager.isLikedIn(userId, quizIds)
    }
}
