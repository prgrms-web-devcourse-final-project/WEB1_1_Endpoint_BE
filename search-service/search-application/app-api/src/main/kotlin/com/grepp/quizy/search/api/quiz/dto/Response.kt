package com.grepp.quizy.search.api.quiz.dto

import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.quiz.SearchedQuiz

data class SearchedQuizResponse(
        val quizzes: List<SearchedQuiz>,
        val hasNext: Boolean,
) {
    companion object {
        fun from(slice: Slice<SearchedQuiz>) =
                SearchedQuizResponse(slice.content, slice.hasNext)
    }
}
