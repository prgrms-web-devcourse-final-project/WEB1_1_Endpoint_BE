package com.grepp.quizy.matching.domain.user

@JvmInline value class UserId(val value: Long)

@JvmInline value class UserVector(val value: FloatArray)

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

enum class InterestCategory(val index: Int) {
    ALGORITHM(0),
    PROGRAMMING_LANGUAGE(1),
    NETWORK(2),
    OPERATING_SYSTEM(3),
    WEB_DEVELOPMENT(4),
    MOBILE(5),
    DEV_OPS(6),
    DATABASE(7),
    SOFTWARE_ENGINEERING(8);

    companion object {
        const val VECTOR_START_INDEX = 6

        fun commonInterest(indexes: List<Int>) =
            if (indexes.isEmpty()) entries.random() else InterestCategory.of(indexes.random() - VECTOR_START_INDEX)

        fun of(index: Int) = entries.first { it.index == index }
    }
}