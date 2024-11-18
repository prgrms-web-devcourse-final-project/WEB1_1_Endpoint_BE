package com.grepp.quizy.search.infra.quiz.document

sealed interface QuizDocumentVo

data class QuizOptionVO(val optionNumber: Int, val content: String) : QuizDocumentVo

data class QuizAnswerVO(val value: String, val explanation: String) : QuizDocumentVo
