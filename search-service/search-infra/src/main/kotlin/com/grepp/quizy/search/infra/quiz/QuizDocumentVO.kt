package com.grepp.quizy.search.infra.quiz

import com.grepp.quizy.search.domain.quiz.QuizType

sealed interface QuizDocumentVo

data class QuizContentVO(val type: QuizType, val content: String) : QuizDocumentVo

data class QuizOptionVO(val optionNumber: Int, val content: String) : QuizDocumentVo

data class QuizAnswerVO(val value: String, val explanation: String) : QuizDocumentVo
