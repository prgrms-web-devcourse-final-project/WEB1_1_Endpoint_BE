package com.grepp.quizy.quiz.api.like.dto

import com.grepp.quizy.quiz.domain.like.LikeStatus

data class LikeStatusResponse(
        val quizId: Long,
        val isLiked: Boolean,
) {
    companion object {
        fun from(likeStatus: LikeStatus) =
                LikeStatusResponse(
                        likeStatus.quizId.value,
                        likeStatus.isLiked,
                )
    }
}
