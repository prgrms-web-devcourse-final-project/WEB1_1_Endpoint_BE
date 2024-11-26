package com.grepp.quizy.quiz.domain.quiz

@JvmInline value class QuizId(val value: Long) {}

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
        val selectionCount: Int = 0,
)

data class QuizContent(
        val category: QuizCategory,
        val content: String,
        val tags: List<QuizTag>,
        val options: List<QuizOption>,
) {

    init {
        require(content.isNotBlank()) { "퀴즈 내용은 공백일 수 없습니다" }
    }

    fun updateTags(tags: List<QuizTag>): QuizContent =
            copy(tags = tags)
}

data class QuizAnswer(val value: String, val explanation: String) {

    fun isCorrect(userAnswer: String): Boolean = value == userAnswer

    companion object {
        val EMPTY: QuizAnswer = QuizAnswer("", "")
    }
}

data class QuizCount(val like: Long = 0, val comment: Long = 0)

enum class QuizType(val value: String) {
    AB_TEST("AB 테스트"),
    OX("OX 퀴즈"),
    MULTIPLE_CHOICE("객관식"),
}

enum class QuizCategory(val description: String) {
    ALGORITHM("알고리즘"),
    PROGRAMMING_LANGUAGE("프로그래밍 언어"),
    NETWORK("네트워크"),
    OPERATING_SYSTEM("운영체제"),
    WEB_DEVELOPMENT("웹 개발"),
    MOBILE("모바일"),
    DEV_OPS("데브옵스"),
    DATABASE("데이터베이스"),
    SOFTWARE_ENGINEERING("소프트웨어 공학"),
}
