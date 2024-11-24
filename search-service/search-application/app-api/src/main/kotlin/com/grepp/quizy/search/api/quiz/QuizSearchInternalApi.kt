package com.grepp.quizy.search.api.quiz

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.search.api.quiz.dto.GameSetResponse
import com.grepp.quizy.search.domain.quiz.GameQuizSearchCondition
import com.grepp.quizy.search.domain.quiz.GameQuizSearchUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/internal/search/quiz")
class QuizSearchInternalApi(private val gameQuizSearchUseCase: GameQuizSearchUseCase) {

    @GetMapping("/game-set")
    fun searchGameQuizSet(condition: GameQuizSearchCondition): ApiResponse<GameSetResponse> =
        ApiResponse.success(GameSetResponse(gameQuizSearchUseCase.searchForPrivateGame(condition)))
}