package com.grepp.quizy.game.api.user.dto

data class UserRatingResponse(
    val rating: Int
) {
    companion object {
        fun from(rating: Int): UserRatingResponse {
            return UserRatingResponse(rating)
        }
    }
}