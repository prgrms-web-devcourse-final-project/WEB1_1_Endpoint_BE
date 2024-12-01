package com.grepp.quizy.quiz.api.like

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.like.dto.LikeRequest
import com.grepp.quizy.quiz.domain.like.Like
import com.grepp.quizy.quiz.domain.like.LikeService
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/quiz/likes")
class LikeApi(private val likeService: LikeService) {

    @PostMapping
    fun toggleLike(
        @AuthUser principal: UserPrincipal,
        @RequestBody request: LikeRequest,
    ): ApiResponse<Boolean> =
            ApiResponse.success(
                    likeService.toggleLike(request.toLike(principal.value))
            )
}
