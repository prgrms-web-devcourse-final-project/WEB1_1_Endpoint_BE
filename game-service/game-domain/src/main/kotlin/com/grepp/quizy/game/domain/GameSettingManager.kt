package com.grepp.quizy.game.domain

import org.springframework.stereotype.Component

@Component
class GameSettingManager(
    private val gameRepository: GameRepository
) {

    fun updateSubject(game: Game, subject: GameSubject, userId: Long): Game {
        val currentGame = game.updateSubject(
            userId = userId,
            subject = subject
        )
        return gameRepository.save(currentGame)
    }

    fun updateLevel(game: Game, level: GameLevel, userId: Long): Game {
        val currentGame = game.updateLevel(
            userId = userId,
            level = level
        )
        return gameRepository.save(currentGame)
    }

    fun updateQuizCount(game: Game, quizCount: Int, userId: Long): Game {
        val currentGame = game.updateQuizCount(
            userId = userId,
            quizCount = quizCount
        )
        return gameRepository.save(currentGame)
    }

}