package com.grepp.quizy.search.api.quiz

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.search.api.quiz.dto.SearchParams
import com.grepp.quizy.search.api.quiz.dto.SearchedQuizResponse
import com.grepp.quizy.search.domain.quiz.QuizSearchUseCase
import com.grepp.quizy.search.domain.user.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/search/quiz")
class QuizSearchApi(private val quizSearchUseCase: QuizSearchUseCase) {

    @GetMapping
    fun searchByKeyword(
        @RequestHeader("X-Auth-Id") userId: UserId?,
        params: SearchParams
    ): ApiResponse<SearchedQuizResponse> =
        ApiResponse.success(SearchedQuizResponse.from(quizSearchUseCase.searchByKeyword(userId, params.SearchCondition())))
}