package com.grepp.quizy.game.domain

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class FakeGameRepository : GameRepository {

    private val games = ConcurrentHashMap<Long, Game>()
    private var sequence = AtomicLong(0)

    override fun save(game: Game): Game {
        val savedGame = if (game.id == 0L) {
            Game(
                id = sequence.incrementAndGet(),
                type = game.type,
                inviteCode = game.inviteCode,
                _setting = game.setting,
                _status = game.status,
                _players = game.players,
            )
        } else {
            game
        }
        games[savedGame.id] = savedGame
        return savedGame
    }

    override fun findById(id: Long): Game? {
        return games[id]
    }

    override fun findByInviteCode(code: String): Game? {
        return games.values.find { it.inviteCode?.value == code }
    }

    fun clear() {
        games.clear()
    }
}