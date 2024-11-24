package com.grepp.quizy.quiz.api.quizread

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.quizread.dto.UserSearchParams
import com.grepp.quizy.quiz.api.quizread.dto.SearchedQuizResponse
import com.grepp.quizy.quiz.domain.quizread.UserQuizSearchUseCase
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz/search")
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
