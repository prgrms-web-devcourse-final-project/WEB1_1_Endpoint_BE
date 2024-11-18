package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.global.dto.SearchCondition
import com.grepp.quizy.search.domain.global.dto.Slice

interface QuizSearchUseCase {
    fun searchByKeyword(condition: SearchCondition): Slice<SearchedQuiz>
}