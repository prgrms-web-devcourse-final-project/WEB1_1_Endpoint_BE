package com.grepp.quizy.quiz.api.quiz.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.grepp.quizy.quiz.domain.quiz.QuizOption

@JsonInclude(Include.NON_NULL)
class QuizOptionResponse(
        val optionNumber: Int,
        val content: String,
        val imageId: Long?,
        val selectionCount: Long
) {
    companion object {
        fun from(option: QuizOption): QuizOptionResponse {
            return QuizOptionResponse(
                    optionNumber = option.optionNumber,
                    content = option.content,
                    imageId = when (option) {
                        is QuizOption.ABTestOption -> option.imageId?.value
                        else -> null
                    },
                    selectionCount = option.selectionCount
            )
        }
    }
}