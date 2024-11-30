package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.user.User
import org.springframework.stereotype.Component

@Component
class GameSettingManager(private val gameRepository: GameRepository) {

    fun updateSubject(
        game: Game,
        subject: String,
        userId: Long,
    ): Game {
        game.updateSubject(userId, GameSubject.fromString(subject))
        return gameRepository.save(game)
    }

    fun updateLevel(
        game: Game,
        level: String,
        userId: Long,
    ): Game {
        game.updateLevel(userId, GameLevel.fromString(level))
        return gameRepository.save(game)
    }

    fun updateQuizCount(
        game: Game,
        quizCount: Int,
        userId: Long,
    ): Game {
        game.updateQuizCount(userId = userId, quizCount = quizCount)
        return gameRepository.save(game)
    }

    fun gameStart(
        game: Game,
        userId: Long
    ): Game {
        game.start(userId)
        return gameRepository.save(game)
    }
}
