package com.grepp.quizy.quiz.api.quizread

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.quizread.dto.FeedSearchParams
import com.grepp.quizy.quiz.api.quizread.dto.QuizFeedResponse
import com.grepp.quizy.quiz.domain.quizread.UserQuizReadUseCase
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz/my")
class QuizMyApi(
    private val userQuizReadUseCase: UserQuizReadUseCase
) {

    @GetMapping("/feed")
    fun getQuizPersonalFeed(@RequestHeader("X-Auth-Id") userId: UserId?, params: FeedSearchParams) =
        ApiResponse.success(
            QuizFeedResponse.from(
                userQuizReadUseCase.searchRecommendedFeed(
                    userId = userId,
                    condition = params.FeedSearchCondition()
                )
            )
        )
}