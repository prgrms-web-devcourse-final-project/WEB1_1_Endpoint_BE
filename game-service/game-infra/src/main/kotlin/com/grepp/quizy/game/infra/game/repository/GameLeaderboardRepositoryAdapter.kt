package com.grepp.quizy.game.infra.game.repository

import com.grepp.quizy.game.domain.game.GameLeaderboardRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.stereotype.Repository

@Repository
class GameLeaderboardRepositoryAdapter(
    private val redisTemplate: RedisTemplate<String, String>,
) : GameLeaderboardRepository {

    companion object {
        private const val LEADERBOARD_KEY = "game:%d:leaderboard"
        private const val DEFAULT_SCORE = 0.0
    }

    override fun saveAll(gameId: Long, ids: List<Long>) {
        val leaderboardKey = String.format(LEADERBOARD_KEY, gameId)

        val users = ids.map { id ->
            ZSetOperations.TypedTuple.of(
                id.toString(),
                DEFAULT_SCORE
            )
        }.toSet()

        redisTemplate.opsForZSet().add(leaderboardKey, users)
    }

    override fun increaseScore(gameId: Long, userId: Long, score: Int) {
        val leaderboardKey = String.format(LEADERBOARD_KEY, gameId)

        redisTemplate.opsForZSet().incrementScore(leaderboardKey, userId.toString(), score.toDouble())
    }

    override fun findAll(gameId: Long): Map<Long, Int> {
        val leaderboardKey = String.format(LEADERBOARD_KEY, gameId)

        return redisTemplate.opsForZSet().reverseRangeWithScores(leaderboardKey, 0, -1)
            ?.mapNotNull {
                it.value?.toLong()?.let { value ->
                    it.score?.let { score ->
                        value to score.toInt()
                    }
                }
            }
            ?.toMap()
            ?: emptyMap()
    }

    override fun findRank(gameId: Long, userId: Long): Long? {
        val leaderboardKey = String.format(LEADERBOARD_KEY, gameId)

        return redisTemplate.opsForZSet().reverseRank(leaderboardKey, userId.toString())
    }
}