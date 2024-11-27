package com.grepp.quizy.game.domain.game

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class FakeGameRepository : GameRepository, GameQuizRepository, GameLeaderboardRepository {

    private val games = ConcurrentHashMap<Long, Game>()
    private val gameSet = ConcurrentHashMap<Long, MutableSet<Long>>()
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

    override fun saveQuiz(gameId: Long, quizId: Long): Long? {
        val game = games[gameId] ?: return null
        gameSet.putIfAbsent(gameId, mutableSetOf())
        if (gameSet[gameId]?.contains(quizId) == true) {
            return 0
        } else {
            gameSet[gameId]?.add(quizId)
            return 1
        }
    }

    override fun findQuizzes(gameId: Long): Set<Long> {
        return gameSet[gameId] ?: emptySet()
    }

    override fun saveAll(gameId: Long, ids: List<Long>) {
        TODO("Not yet implemented")
    }

    override fun increaseScore(gameId: Long, userId: Long, score: Double) {
        TODO("Not yet implemented")
    }

    override fun findAll(gameId: Long): Map<Long, Double> {
        TODO("Not yet implemented")
    }

    fun clear() {
        games.clear()
    }
}