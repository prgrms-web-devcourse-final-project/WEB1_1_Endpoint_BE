package com.grepp.quizy.quiz.api.like

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.like.dto.LikeRequest
import com.grepp.quizy.quiz.domain.like.LikeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
