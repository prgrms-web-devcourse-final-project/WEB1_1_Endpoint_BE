package com.grepp.quizy.user.infra.feign

import com.grepp.quizy.user.domain.game.GameClient
import com.grepp.quizy.user.domain.game.UserRating
import com.grepp.quizy.user.domain.quiz.QuizClient
import com.grepp.quizy.user.domain.quiz.UserQuizScore
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "quiz-service", url = "\${feign.client.url.quiz-service}")
interface QuizServiceClient : QuizClient {
    @GetMapping("/api/quiz/internal/users/{userId}")
    override fun getUserQuizScore(@PathVariable userId: Long): UserQuizScore
}

@FeignClient(name = "game-service", url = "\${feign.client.url.game-service}")
interface GameServiceClient : GameClient {
    @GetMapping("/api/internal/user/rating/{userId}")
    override fun getUserRating(@PathVariable userId: Long): UserRating
}