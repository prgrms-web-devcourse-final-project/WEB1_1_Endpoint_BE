package com.grepp.quizy.user.domain.game

interface GameClient {
    fun getUserRating(userId: Long): UserRating
}