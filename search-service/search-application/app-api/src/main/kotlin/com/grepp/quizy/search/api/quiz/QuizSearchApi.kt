package com.grepp.quizy.search.api.quiz

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.search.api.quiz.dto.UserSearchParams
import com.grepp.quizy.search.api.quiz.dto.SearchedQuizResponse
import com.grepp.quizy.search.domain.quiz.UserQuizSearchUseCase
import com.grepp.quizy.search.domain.user.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/search/quiz")
class QuizSearchApi(
        private val userQuizSearchUseCase: UserQuizSearchUseCase
) {

    @GetMapping
    fun searchByKeyword(
        @RequestHeader("X-Auth-Id") userId: UserId?,
        params: UserSearchParams,
    ): ApiResponse<SearchedQuizResponse> =
            ApiResponse.success(
                    SearchedQuizResponse.from(
                            userQuizSearchUseCase.searchByKeyword(
                                    userId,
                                    params.UserSearchCondition(),
                            )
                    )
            )
}
