package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.quiz.domain.image.QuizImageId
import com.grepp.quizy.quiz.domain.quiz.QuizAnswer
import com.grepp.quizy.quiz.domain.quiz.QuizOption
import com.grepp.quizy.quiz.domain.quiz.QuizType
import jakarta.persistence.Embeddable

@Embeddable
data class QuizOptionVO(
        val optionNumber: Int,
        val content: String,
        val imageId: Long? = null,
) {

    fun toDomain(quizType: QuizType): QuizOption {
        return when (quizType) {
            QuizType.AB_TEST -> QuizOption.ABTestOption(
                    optionNumber = optionNumber,
                    content = content,
                    imageId = imageId?.let { QuizImageId.from(it) },
            )
            QuizType.MULTIPLE_CHOICE -> QuizOption.MultipleChoiceOption(
                    optionNumber = optionNumber,
                    content = content,
            )
            QuizType.OX -> QuizOption.OXOption(
                    optionNumber = optionNumber,
                    content = content,
            )
        }
    }

    companion object {
        fun from(option: QuizOption): QuizOptionVO {
            return when (option) {
                is QuizOption.ABTestOption -> QuizOptionVO(
                        optionNumber = option.optionNumber,
                        content = option.content,
                        imageId = option.imageId?.value,
                )
                is QuizOption.MultipleChoiceOption -> QuizOptionVO(
                        optionNumber = option.optionNumber,
                        content = option.content,
                )
                is QuizOption.OXOption -> QuizOptionVO(
                        optionNumber = option.optionNumber,
                        content = option.content,
                )
            }
        }
    }
}

@Embeddable
data class QuizAnswerVO(val answer: String, val explanation: String) {

    fun toDomain(): QuizAnswer {
        return QuizAnswer(value = answer, explanation = explanation)
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
