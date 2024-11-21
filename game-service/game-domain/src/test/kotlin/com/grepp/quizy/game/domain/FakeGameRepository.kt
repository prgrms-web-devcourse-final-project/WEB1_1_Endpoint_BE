package com.grepp.quizy.game.domain

class FakeGameRepository : GameRepository {

    private val games = mutableListOf<Game>()

    override fun save(game: Game): Game {
        val index = games.indexOfFirst { it.id == game.id }
        if (index == -1) {
            games.add(game)
        } else {
            games[index] = game
        }
        return game
    }

    override fun findById(id: Long): Game? {
        return games.find { it.id == id }
    }

    override fun findByInviteCode(code: String): Game? {
        return games.find { it.inviteCode.value == code }
    }

    fun clear() {
        games.clear()
    }
}