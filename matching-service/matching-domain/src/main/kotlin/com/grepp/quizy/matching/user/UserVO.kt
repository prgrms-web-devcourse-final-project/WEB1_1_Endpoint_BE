package com.grepp.quizy.matching.user

@JvmInline value class UserId(val value: Long)

@JvmInline value class UserVector(val value: FloatArray)

enum class GameRating(val index: Float) {
    BRONZE(1.0f), SILVER(2.0f), GOLD(3.0f), PLATINUM(4.0f), DIAMOND(5.0f), MASTER(6.0f);

    companion object {
        const val VECTOR_INDEX = 0
    }
}

enum class InterestCategory(val index: Int) {
    ALGORITHM(1),
    PROGRAMMING_LANGUAGE(2),
    NETWORK(3),
    OPERATING_SYSTEM(4),
    WEB_DEVELOPMENT(5),
    MOBILE(6),
    DEV_OPS(7),
    DATABASE(8),
    SOFTWARE_ENGINEERING(9);

    companion object {
        const val NUM_CATEGORIES = 9
    }
}