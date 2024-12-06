package com.grepp.quizy.matching.domain.game

@JvmInline value class GameRoomId(val value: Long)

enum class GameRating(val index: Int) {
    BRONZE(0),
    SILVER(1),
    GOLD(2),
    PLATINUM(3),
    DIAMOND(4),
    MASTER(5);

    companion object {

        fun fromRatingValue(rating: Int): GameRating {
            return when {
                rating < 500 -> BRONZE
                rating in 501..1000 -> SILVER
                rating in 1001..1500 -> GOLD
                rating in 1501..2000 -> PLATINUM
                rating in 2001..2500 -> DIAMOND
                else -> MASTER
            }
        }
    }
}