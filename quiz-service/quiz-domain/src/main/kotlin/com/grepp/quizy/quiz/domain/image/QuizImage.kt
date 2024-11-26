package com.grepp.quizy.quiz.domain.image

@JvmInline
value class QuizImageId(val value: Long) {
    companion object {
        val NOT_ASSIGNED = QuizImageId(0)

        fun from(id: Long): QuizImageId {
            return QuizImageId(id)
        }
    }
}


data class QuizImage(
    val url: String,
    val id: QuizImageId = QuizImageId.NOT_ASSIGNED,
) {
    companion object {
        fun from(url: String): QuizImage {
            return QuizImage(url)
        }
    }
}


