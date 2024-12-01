package com.grepp.quizy.quiz.api.quizread

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.quizread.dto.FeedSearchParams
import com.grepp.quizy.quiz.api.quizread.dto.QuizSliceResponse
import com.grepp.quizy.quiz.domain.quizread.UserQuizReadUseCase
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz/my")
class QuizMyApi(
    private val userQuizReadUseCase: UserQuizReadUseCase
) {

    @GetMapping("/feed")
    fun getQuizPersonalFeed(@AuthUser principal: UserPrincipal, params: FeedSearchParams) =
        ApiResponse.success(
            QuizSliceResponse.from(
                userQuizReadUseCase.searchRecommendedFeed(
                    userId = UserId(principal.value),
                    condition = params.FeedSearchCondition()
                )
            )
        )
}