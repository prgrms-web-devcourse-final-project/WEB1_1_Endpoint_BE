package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.user.User
import org.springframework.stereotype.Component

@Component
class GameSettingManager(private val gameRepository: GameRepository) {

    fun updateSubject(
        game: Game,
        subject: GameSubject,
        user: User,
    ): Game {
        game.updateSubject(user = user, subject = subject)
        return gameRepository.save(game)
    }

    fun updateLevel(
        game: Game,
        level: GameLevel,
        user: User,
    ): Game {
        game.updateLevel(user = user, level = level)
        return gameRepository.save(game)
    }

    fun updateQuizCount(
        game: Game,
        quizCount: Int,
        user: User,
    ): Game {
        game.updateQuizCount(user = user, quizCount = quizCount)
        return gameRepository.save(game)
    }
}
