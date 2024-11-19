package com.grepp.quizy.game.domain

import org.springframework.stereotype.Component

@Component
class GameSettingManager(
    private val gameRepository: GameRepository
) {

    fun updateSubject(game: Game, subject: GameSubject, userId: Long): Game {
        game.updateSubject(
            userId = userId,
            subject = subject
        )
        return gameRepository.save(game)
    }

    fun updateLevel(game: Game, level: GameLevel, userId: Long): Game {
        game.updateLevel(
            userId = userId,
            level = level
        )
        return gameRepository.save(game)
    }

    fun updateQuizCount(game: Game, quizCount: Int, userId: Long): Game {
        game.updateQuizCount(
            userId = userId,
            quizCount = quizCount
        )
        return gameRepository.save(game)
    }

}