package com.grepp.quizy.search.infra.quiz.document

enum class SortField(val fieldName: String) {
    TRENDING("totalAnsweredUser"), NEW("createdAt");
}