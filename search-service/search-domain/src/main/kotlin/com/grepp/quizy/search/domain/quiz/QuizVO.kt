package com.grepp.quizy.search.domain.quiz

@JvmInline value class QuizId(val value: Long)

@JvmInline value class QuizTag(val value: String)

@JvmInline value class QuizCategory(val value: String)

data class QuizContent(val type: QuizType, val value: String)

data class QuizOption(val optionNumber: Int, val content: String, val selectionCount: Int)

data class QuizAnswer(val value: String, val explanation: String)

enum class QuizType(val typeName: String) {
    OX("OX 퀴즈"), AB("A/B 밸런스"), MULTIPLE_CHOICE("객관식");

    companion object {
        fun fromType(typeName: String): QuizType =
            entries.find { type -> type.typeName == typeName } ?: throw IllegalArgumentException("Unknown type $typeName")
    }
}