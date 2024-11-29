package com.grepp.quizy.game.domain.game

interface GameLeaderboardRepository {

    fun saveAll(gameId: Long, ids: List<Long>)

    fun increaseScore(gameId: Long, userId: Long, score: Double)

    fun findAll(gameId: Long): Map<Long, Double>

}