package com.grepp.quizy.quiz.infra.quizread.document

import com.grepp.quizy.quiz.domain.quizread.QuizSortType

sealed interface QuizDocumentVo

data class QuizOptionVO(val optionNumber: Int, val content: String) :
        QuizDocumentVo

data class QuizAnswerVO(val value: String, val explanation: String) :
        QuizDocumentVo

enum class QuizDifficulty {
        EASY, MEDIUM, HARD, NONE
}

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
