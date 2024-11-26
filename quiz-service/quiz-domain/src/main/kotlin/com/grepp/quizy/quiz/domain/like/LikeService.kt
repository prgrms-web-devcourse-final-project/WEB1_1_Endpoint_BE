package com.grepp.quizy.quiz.domain.like

import org.springframework.stereotype.Service

@Service
class LikeService(private val likeManager: LikeManager) {

    fun toggleLike(like: Like): Boolean {
        return likeManager.toggleLike(like)
    }
}
