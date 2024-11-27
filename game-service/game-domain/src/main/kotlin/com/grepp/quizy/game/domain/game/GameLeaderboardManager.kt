package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component

@Component
class GameLeaderboardManager(
    private val gameLeaderboardRepository: GameLeaderboardRepository
) {

    fun initializeLeaderboard(gameId: Long, playerIds: List<Long>) {
        gameLeaderboardRepository.saveAll(gameId, playerIds)
    }

}