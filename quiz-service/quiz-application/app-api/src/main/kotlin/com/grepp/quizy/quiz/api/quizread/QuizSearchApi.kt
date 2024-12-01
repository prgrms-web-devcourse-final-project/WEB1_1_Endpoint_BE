package com.grepp.quizy.quiz.api.quizread

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.quizread.dto.UserSearchParams
import com.grepp.quizy.quiz.api.quizread.dto.QuizSliceResponse
import com.grepp.quizy.quiz.domain.quizread.UserQuizSearchUseCase
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
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
    ): ApiResponse<QuizSliceResponse> =
            ApiResponse.success(
                    QuizSliceResponse.from(
                            userQuizSearchUseCase.searchByKeyword(
                                    userId,
                                    params.UserSearchCondition(),
                            )
                    )
            )

    @GetMapping("/unanswered")
    fun searchUnansweredByKeyword(
        @AuthUser principal: UserPrincipal,
        params: UserSearchParams,
    ): ApiResponse<QuizSliceResponse> =
        ApiResponse.success(
            QuizSliceResponse.from(
                userQuizSearchUseCase.searchUnansweredByKeyword(
                    UserId(principal.value),
                    params.UserSearchCondition(),
                )
            )
        )
}
