package com.grepp.quizy.game.domain

import com.grepp.quizy.game.domain.exception.GameException
import org.springframework.stereotype.Component

@Component
class GameReader(
    private val gameRepository: GameRepository
) {

    fun read(id: Long): Game {
        return gameRepository.findById(id) ?: throw GameException.GameNotFoundException
    }

    fun readByInviteCode(code: String): Game {
        return gameRepository.findByInviteCode(code) ?: throw GameException.GameNotFoundException
    }

}