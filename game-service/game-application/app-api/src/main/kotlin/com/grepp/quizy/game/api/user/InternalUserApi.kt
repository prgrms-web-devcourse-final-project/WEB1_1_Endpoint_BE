package com.grepp.quizy.game.api.user

import com.grepp.quizy.game.api.user.dto.UserRatingResponse
import com.grepp.quizy.game.domain.user.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/internal/user")
class InternalUserApi(
    private val userService: UserService
) {

    @GetMapping("/rating/{userId}")
    fun getUserRating(
        @PathVariable userId: Long
    ): UserRatingResponse {
        return UserRatingResponse.from(userService.getUserRating(userId))
    }

}