package com.grepp.quizy.quiz.api.user

import com.grepp.quizy.quiz.api.user.dto.UserStatsResponse
import com.grepp.quizy.quiz.domain.user.QuizUserService
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz/internal/users")
class QuizUserInternalApi(
    private val quizUserService: QuizUserService
) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserStatsResponse =
        UserStatsResponse.from(quizUserService.getUser(UserId(id)))
}