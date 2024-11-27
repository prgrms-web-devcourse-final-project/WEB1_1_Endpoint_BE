package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameQuizAppender(
    private val gameQuizRepository: GameQuizRepository,
) {

    fun appendQuiz(
        gameId: Long,
        quizId: Long
    ) {
        gameQuizRepository.saveQuiz(gameId, quizId) ?: throw IllegalStateException("퀴즈 추가 실패")
    }

}