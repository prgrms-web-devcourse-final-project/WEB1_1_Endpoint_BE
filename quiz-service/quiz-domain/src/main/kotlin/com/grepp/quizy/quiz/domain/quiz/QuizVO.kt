package com.grepp.quizy.quiz.domain.quiz

@JvmInline value class QuizId(val value: Long)

@JvmInline value class QuizTagId(val value: Long)

data class QuizTag(
        val name: String,
        val id: QuizTagId = QuizTagId(0),
) {
    companion object {
        fun create(name: String): QuizTag {
            return QuizTag(name)
        }

        fun of(name: String, id: QuizTagId): QuizTag {
            return QuizTag(name, id)
        }
    }
}

data class QuizOption(
        val optionNumber: Int,
        val title: String,
        val content: String,
)

data class QuizContent(
        val content: String,
        val tags: List<QuizTag>,
        val options: List<QuizOption>,
) {

    init {
        require(content.isNotBlank()) {
            "퀴즈 내용은 공백일 수 없습니다"
        }
    }

    fun updateTags(tags: List<QuizTag>): QuizContent =
            copy(tags = tags)
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
