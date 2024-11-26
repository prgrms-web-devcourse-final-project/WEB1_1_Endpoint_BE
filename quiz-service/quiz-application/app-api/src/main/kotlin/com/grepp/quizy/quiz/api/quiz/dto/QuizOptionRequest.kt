package com.grepp.quizy.quiz.api.quiz.dto

import com.grepp.quizy.quiz.domain.image.QuizImageId
import com.grepp.quizy.quiz.domain.quiz.QuizOption
import com.grepp.quizy.quiz.domain.quiz.QuizType

data class QuizOptionRequest(
    val optionNumber: Int,
    val content: String,
    val imageId: Long?,
) {
    fun toOption(quizType: QuizType): QuizOption {
        return when (quizType) {
            QuizType.AB_TEST -> QuizOption.ABTestOption(
                optionNumber = optionNumber,
                content = content,
                imageId = imageId?.let { QuizImageId.from(it) } ?: throw IllegalArgumentException("AB 테스트 문제는 이미지가 필수입니다")
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
}