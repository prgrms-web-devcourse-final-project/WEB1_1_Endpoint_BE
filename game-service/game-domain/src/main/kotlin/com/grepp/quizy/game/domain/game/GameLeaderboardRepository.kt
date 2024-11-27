package com.grepp.quizy.game.domain.game

interface GameLeaderboardRepository {

    fun saveAll(gameId: Long, ids: List<Long>)

}