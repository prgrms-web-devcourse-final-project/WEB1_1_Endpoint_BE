package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameQuizAppender(
    private val gameRepository: GameRepository,
) {

    fun appendQuiz(
        gameId: Long,
        quizId: Long
    ) {
        gameRepository.saveQuiz(gameId, quizId)
    }

}