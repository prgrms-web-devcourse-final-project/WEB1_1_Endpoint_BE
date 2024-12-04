package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameLeaderboardManager(
    private val gameLeaderboardRepository: GameLeaderboardRepository
) {

    fun initializeLeaderboard(gameId: Long, userIds: List<Long>) {
        gameLeaderboardRepository.saveAll(gameId, userIds)
    }

    fun incrementScore(gameId: Long, userId: Long, score: Int) {
        gameLeaderboardRepository.increaseScore(gameId, userId, score)
    }

    fun getLeaderboard(gameId: Long): Map<Long, Int> {
        return gameLeaderboardRepository.findAll(gameId)
    }

    fun getRank(gameId: Long, userId: Long): Int {
        val rank = gameLeaderboardRepository.findRank(gameId, userId)
        return rank?.toInt()?.plus(1) ?: 0
    }

}