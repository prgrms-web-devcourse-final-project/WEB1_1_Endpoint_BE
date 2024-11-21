package com.grepp.quizy.search.domain.quiz

enum class QuizType(val typeName: String) {
    OX("OX 퀴즈"),
    AB("A/B 밸런스"),
    MULTIPLE_CHOICE("객관식");

    companion object {
        fun fromType(typeName: String): QuizType =
                entries.find { type -> type.typeName == typeName }
                        ?: throw IllegalArgumentException(
                                "Unknown type $typeName"
                        )
    }
}
