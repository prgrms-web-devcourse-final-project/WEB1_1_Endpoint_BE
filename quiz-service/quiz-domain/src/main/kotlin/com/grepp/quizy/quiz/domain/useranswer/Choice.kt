package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.QuizType

sealed class Choice {
    abstract val value: String

    data class OXChoice(
            override val value: String,
            val isCorrect: Boolean,
    ) : Choice() {
        init {
            require(value in setOf("O", "X")) {
                "OX 퀴즈의 선택지는 O 또는 X여야 합니다"
            }
        }
    }

    data class ABChoice(override val value: String) : Choice() {
        init {
            require(value in setOf("A", "B")) {
                "AB 테스트의 선택지는 A 또는 B여야 합니다"
            }
        }
    }

    data class MultipleChoice(
            override val value: String,
            val isCorrect: Boolean,
    ) : Choice() {
        init {
            require(value.toIntOrNull() != null) {
                "객관식 문항의 선택지는 숫자여야 합니다"
            }
        }
    }

    companion object {
        fun create(
                type: QuizType,
                value: String,
                isCorrect: Boolean,
        ): Choice {
            return when (type) {
                QuizType.OX -> OXChoice(value, isCorrect)
                QuizType.MULTIPLE_CHOICE ->
                        MultipleChoice(value, isCorrect)
                QuizType.AB_TEST ->
                        throw IllegalArgumentException(
                                "AB 테스트는 선택지가 없습니다"
                        )
            }
        }

        fun create(type: QuizType, value: String): Choice {
            return when (type) {
                QuizType.AB_TEST -> ABChoice(value)
                QuizType.OX,
                QuizType.MULTIPLE_CHOICE ->
                        throw IllegalArgumentException(
                                "OX 퀴즈와 객관식 문항은 정답 여부가 필요합니다."
                        )
            }
        }
    }
}
