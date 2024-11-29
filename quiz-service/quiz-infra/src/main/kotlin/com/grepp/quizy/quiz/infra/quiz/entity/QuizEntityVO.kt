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
        var selectionCount: Long = 0,
) {

    fun toDomain(quizType: QuizType): QuizOption {
        return when (quizType) {
            QuizType.AB_TEST -> QuizOption.ABTestOption(
                    optionNumber = optionNumber,
                    content = content,
                    imageId = imageId?.let { QuizImageId.from(it) },
                    selectionCount = selectionCount,
            )
            QuizType.MULTIPLE_CHOICE -> QuizOption.MultipleChoiceOption(
                    optionNumber = optionNumber,
                    content = content,
                    selectionCount = selectionCount,
            )
            QuizType.OX -> QuizOption.OXOption(
                    optionNumber = optionNumber,
                    content = content,
                    selectionCount = selectionCount,
            )
        }
    }

    fun increaseSelectionCount(count: Long) {
        this.selectionCount += count
    }

    companion object {
        fun from(option: QuizOption): QuizOptionVO {
            return when (option) {
                is QuizOption.ABTestOption -> QuizOptionVO(
                        optionNumber = option.optionNumber,
                        content = option.content,
                        imageId = option.imageId?.value,
                        selectionCount = option.selectionCount,
                )
                is QuizOption.MultipleChoiceOption -> QuizOptionVO(
                        optionNumber = option.optionNumber,
                        content = option.content,
                        selectionCount = option.selectionCount,
                )
                is QuizOption.OXOption -> QuizOptionVO(
                        optionNumber = option.optionNumber,
                        content = option.content,
                        selectionCount = option.selectionCount,
                )
            }
        }
    }
}

@Embeddable
data class QuizAnswerVO(
    val answerNumber: Int,
    val explanation: String
) {

    fun toDomain(): QuizAnswer {
        return QuizAnswer(answerNumber = answerNumber, explanation = explanation)
    }

    companion object {
        fun from(answer: QuizAnswer): QuizAnswerVO {
            return QuizAnswerVO(
                    answerNumber = answer.answerNumber,
                    explanation = answer.explanation,
            )
        }
    }
}
