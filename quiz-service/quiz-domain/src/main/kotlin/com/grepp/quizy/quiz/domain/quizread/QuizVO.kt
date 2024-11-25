package com.grepp.quizy.quiz.domain.quizread

@JvmInline value class QuizId(val value: Long)

@JvmInline value class QuizTag(val value: String)

data class QuizContent(val type: QuizType, val value: String)

data class QuizOption(
        val optionNumber: Int,
        val content: String,
        val selectionCount: Int,
)

data class QuizAnswer(val value: String, val explanation: String)

enum class QuizType(val typeName: String) {
    OX("OX 퀴즈"),
    AB("A/B 밸런스"),
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
