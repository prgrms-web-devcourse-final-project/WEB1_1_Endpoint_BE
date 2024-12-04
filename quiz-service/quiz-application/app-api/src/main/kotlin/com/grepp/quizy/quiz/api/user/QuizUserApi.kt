package com.grepp.quizy.quiz.api.user

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.user.dto.AchievementResponse
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.user.QuizUserService
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/quiz/users")
class QuizUserApi(
    private val quizUserService: QuizUserService
) {

    @PostMapping("/interests")
    fun createInterests(
        @AuthUser userPrincipal: UserPrincipal,
        @RequestBody interests: List<QuizCategory>
    ): ApiResponse<Unit> =
        quizUserService
            .addInterests(UserId(userPrincipal.value), interests)
            .let { ApiResponse.success() }

    @GetMapping("/achievements")
    fun getAchievements(
        @AuthUser userPrincipal: UserPrincipal
    ): ApiResponse<List<AchievementResponse>> =
        ApiResponse.success(
            AchievementResponse.from(
                quizUserService.getAchievements(UserId(userPrincipal.value))
            )
        )


    @PutMapping("/interests")
    fun updateInterests(
        @AuthUser userPrincipal: UserPrincipal,
        @RequestBody interests: List<QuizCategory>
    ): ApiResponse<Unit> =
        quizUserService
            .updateInterests(UserId(userPrincipal.value), interests)
            .let { ApiResponse.success() }
}