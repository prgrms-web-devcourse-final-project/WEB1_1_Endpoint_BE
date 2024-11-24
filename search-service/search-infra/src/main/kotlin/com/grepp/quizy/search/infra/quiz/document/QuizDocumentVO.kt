package com.grepp.quizy.search.infra.quiz.document

import com.grepp.quizy.search.domain.quiz.QuizSortType

sealed interface QuizDocumentVo

data class QuizOptionVO(val optionNumber: Int, val content: String) :
        QuizDocumentVo

data class QuizAnswerVO(val value: String, val explanation: String) :
        QuizDocumentVo

enum class SortField(val fieldName: String) {
        TRENDING("totalAnsweredUser"),
        NEW("createdAt");

        companion object {
                fun from(type: QuizSortType) = when (type) {
                        QuizSortType.TRENDING -> TRENDING
                        QuizSortType.NEW -> NEW
                }
        }
}
