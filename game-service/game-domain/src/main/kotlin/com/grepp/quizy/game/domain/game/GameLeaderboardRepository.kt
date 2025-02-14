package com.grepp.quizy.game.domain.game

interface GameLeaderboardRepository {

    fun saveAll(gameId: Long, ids: List<Long>)

    fun increaseScore(gameId: Long, userId: Long, score: Int)

    fun findAll(gameId: Long): Map<Long, Int>

    fun findRank(gameId: Long, userId: Long): Long?

}