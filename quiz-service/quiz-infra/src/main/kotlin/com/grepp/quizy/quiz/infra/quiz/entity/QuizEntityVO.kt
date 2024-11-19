package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.quiz.domain.quiz.QuizAnswer
import com.grepp.quizy.quiz.domain.quiz.QuizOption
import jakarta.persistence.Embeddable

@Embeddable
data class QuizOptionVO(
        val optionNumber: Int,
        val title: String,
        val content: String,
) {
    protected constructor() : this(0, "", "")

    fun toDomain(): QuizOption {
        return QuizOption(
                optionNumber = optionNumber,
                title = title,
                content = content,
        )
    }

    companion object {
        fun from(option: QuizOption): QuizOptionVO {
            return QuizOptionVO(
                    optionNumber = option.optionNumber,
                    title = option.title,
                    content = option.content,
            )
        }
    }
}

@Embeddable
data class QuizAnswerVO(
        val answer: String,
        val explanation: String,
) {
    protected constructor() : this("", "")

    fun toDomain(): QuizAnswer {
        return QuizAnswer(
                value = answer,
                explanation = explanation,
        )
    }

    companion object {
        fun from(answer: QuizAnswer): QuizAnswerVO {
            return QuizAnswerVO(
                    answer = answer.value,
                    explanation = answer.explanation,
            )
        }
    }
}
