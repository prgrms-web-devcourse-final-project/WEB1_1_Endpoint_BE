package com.grepp.quizy.matching.domain.user

@JvmInline value class UserId(val value: Long)

@JvmInline value class UserVector(val value: FloatArray)


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