package com.grepp.quizy.quiz.domain

@JvmInline value class QuizId(val value: Long)

@JvmInline value class QuizTagId(val value: Long)

data class QuizTag(
        val name: String,
        val id: QuizTagId = QuizTagId(0),
) {
    companion object {
        fun from(tag: String): QuizTag {
            return QuizTag(tag)
        }
    }
}

data class QuizOption(
        val optionNumber: Int,
        val title: String,
        val content: String,
)

data class QuizContent(
        val type: QuizType,
        val content: String,
) {

    init {
        require(content.isNotBlank()) {
            "퀴즈 내용은 공백일 수 없습니다"
        }
    }
}

data class QuizAnswer(
        val value: String,
        val explanation: String,
) {

    fun isCorrect(userAnswer: String): Boolean =
            value == userAnswer

    companion object {
        val EMPTY: QuizAnswer = QuizAnswer("", "")
    }
}

enum class QuizType {
    AB_TEST,
    OX,
    MULTIPLE_CHOICE,
}
