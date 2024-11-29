package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameLeaderboardManager(
    private val gameLeaderboardRepository: GameLeaderboardRepository
) {

    fun initializeLeaderboard(gameId: Long, userIds: List<Long>) {
        gameLeaderboardRepository.saveAll(gameId, userIds)
    }

    fun incrementScore(gameId: Long, userId: Long, score: Double) {
        gameLeaderboardRepository.increaseScore(gameId, userId, score)
    }

    fun getLeaderboard(gameId: Long): Map<Long, Double> {
        return gameLeaderboardRepository.findAll(gameId) ?: emptyMap()
    }

}