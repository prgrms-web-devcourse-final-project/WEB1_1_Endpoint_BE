package com.grepp.quizy.quiz.api.user

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.user.QuizUserService
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.web.annotation.AuthUser
import com.grepp.quizy.web.dto.UserPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


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

    @PutMapping("/interests")
    fun updateInterests(
        @AuthUser userPrincipal: UserPrincipal,
        @RequestBody interests: List<QuizCategory>
    ): ApiResponse<Unit> =
        quizUserService
            .updateInterests(UserId(userPrincipal.value), interests)
            .let { ApiResponse.success() }
}