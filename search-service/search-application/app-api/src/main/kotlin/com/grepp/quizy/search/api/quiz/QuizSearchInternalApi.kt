package com.grepp.quizy.search.api.quiz

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.search.api.quiz.dto.GameSetResponse
import com.grepp.quizy.search.domain.quiz.GameSearchUseCase
import com.grepp.quizy.search.domain.quiz.QuizDifficultyType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/internal/search/quiz")
class QuizSearchInternalApi(private val gameSearchUseCase: GameSearchUseCase) {

    @GetMapping("/game-set")
    fun searchGameQuizSet(category: String, size: Int, difficulty: QuizDifficultyType): ApiResponse<GameSetResponse> =
        ApiResponse.success(GameSetResponse(gameSearchUseCase.searchForPrivateGame(category, size, difficulty)))
}