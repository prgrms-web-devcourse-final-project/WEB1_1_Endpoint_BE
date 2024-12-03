package com.grepp.quizy.game.infra.feign

import com.grepp.quizy.game.domain.game.GameLevel
import com.grepp.quizy.game.domain.game.GameSubject
import com.grepp.quizy.game.domain.quiz.QuizFetcher
import com.grepp.quizy.game.domain.quiz.Quizzes
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "quiz-fetcher", url = "\${feign.client.url.quiz-service}")
interface QuizFetcherFeignClient : QuizFetcher {

    @GetMapping("/api/quiz/internal/game-set")
    override fun fetchQuiz(
       @RequestParam category: GameSubject,
       @RequestParam size: Int,
       @RequestParam difficulty: GameLevel
    ): Quizzes

}