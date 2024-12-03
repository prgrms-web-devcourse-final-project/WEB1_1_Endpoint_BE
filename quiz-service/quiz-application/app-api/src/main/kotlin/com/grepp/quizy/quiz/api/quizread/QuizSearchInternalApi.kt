package com.grepp.quizy.quiz.api.quizread

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.api.quizread.dto.GameQuizSearchParams
import com.grepp.quizy.quiz.api.quizread.dto.GameSetResponse
import com.grepp.quizy.quiz.domain.quizread.GameQuizReadUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz/internal")
class QuizSearchInternalApi(private val gameQuizSearchUseCase: GameQuizReadUseCase) {

    @GetMapping("/game-set")
    fun searchGameQuizSet(params: GameQuizSearchParams) =
            GameSetResponse(
                gameQuizSearchUseCase.searchForPrivateGame(params.GameQuizSearchCondition())
            )
}