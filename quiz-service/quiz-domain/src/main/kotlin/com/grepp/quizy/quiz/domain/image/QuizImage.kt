package com.grepp.quizy.quiz.domain.image

data class QuizImage(
    val url: String,
    val id: Long = 0,
) {
    companion object {
        fun from(url: String): QuizImage {
            return QuizImage(url)
        }
    }
}