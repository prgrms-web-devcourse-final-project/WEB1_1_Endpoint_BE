package com.grepp.quizy.game.infra.feign

import com.grepp.quizy.game.domain.GameLevel
import com.grepp.quizy.game.domain.GameQuiz
import com.grepp.quizy.game.domain.GameSubject
import com.grepp.quizy.game.domain.QuizFetcher
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

// TODO: 퀴즈 서비스의 url 정해지면 추가
@FeignClient(name = "quiz-fetcher", url = "\${quiz-service.url}")
interface QuizFetcherFeignClient : QuizFetcher {

    @GetMapping("/api/internal/search/quiz/game-set")
    override fun fetchQuiz(
        @RequestParam subject: GameSubject,
        @RequestParam quizCount: Int,
        @RequestParam level: GameLevel
    ): GameQuiz

}