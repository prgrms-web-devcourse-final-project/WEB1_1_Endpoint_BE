package com.grepp.quizy.quiz.api.like

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.like.dto.LikeRequest
import com.grepp.quizy.quiz.api.like.dto.LikeStatusResponse
import com.grepp.quizy.quiz.domain.like.LikeService
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quiz/likes")
class LikeApi(private val likeService: LikeService) {

    @PostMapping
    fun toggleLike(
            @RequestBody request: LikeRequest
    ): ApiResponse<Boolean> =
            ApiResponse.success(
                    likeService.toggleLike(request.toLike())
            )

    @GetMapping("/status")
    fun isLiked(
            @RequestParam userId: Long,
            @RequestParam quizIds: List<Long>,
    ): ApiResponse<List<LikeStatusResponse>> =
            ApiResponse.success(
                    likeService
                            .isLikedIn(
                                    UserId(userId),
                                    quizIds.map { QuizId(it) },
                            )
                            .map { LikeStatusResponse.from(it) }
            )
}
