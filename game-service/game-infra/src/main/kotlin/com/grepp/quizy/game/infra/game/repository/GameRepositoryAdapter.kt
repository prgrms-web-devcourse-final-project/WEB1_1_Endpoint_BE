package com.grepp.quizy.game.infra.game.repository

import com.grepp.quizy.game.domain.game.Game
import com.grepp.quizy.game.domain.game.GameRepository
import com.grepp.quizy.game.infra.game.entity.GameRedisEntity
import org.springframework.data.redis.connection.DataType
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.Duration
import java.util.concurrent.TimeUnit

@Repository
class GameRepositoryAdapter(
    private val gameRedisRepository: GameRedisRepository,
    private val redisTemplate: RedisTemplate<String, String>
) : GameRepository {

    override fun save(game: Game): Game {
        val savedGame = gameRedisRepository
            .save(GameRedisEntity.from(game))
            .toDomain()

        savedGame.inviteCode?.let { inviteCode ->
            redisTemplate.opsForValue()
                .set(
                    "invite:${inviteCode.value}",
                    game.id.toString(),
                    Duration.ofHours(1),
                )
        }
        return savedGame
    }

    override fun findById(id: Long): Game? {
        return gameRedisRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun findByInviteCode(code: String): Game? {
        val gameId = redisTemplate.opsForValue().get("invite:$code")?.toLong()
        return gameId?.let { gameRedisRepository.findByIdOrNull(it)?.toDomain() }
    }

    override fun deleteById(id: Long) {
        val inviteCode = gameRedisRepository.findByIdOrNull(id)?.inviteCode
        inviteCode?.let { code ->
            redisTemplate.delete("invite:$code")
        }

        redisTemplate.delete(listOf(
            "game:$id:leaderboard",
            "game:$id:quizzes"
        ))

        val userAnswerPattern = "game:$id:userAnswer:*"
        redisTemplate.connectionFactory?.connection.use { connection ->
            val scanner = connection?.scan(
                ScanOptions.scanOptions()
                    .match(userAnswerPattern)
                    .count(100)
                    .build()
            )

            val keysToDelete = mutableListOf<String>()
            while (scanner!!.hasNext()) {
                keysToDelete.add(String(scanner.next()))
            }

            if (keysToDelete.isNotEmpty()) {
                redisTemplate.delete(keysToDelete)
            }
        }

        gameRedisRepository.deleteById(id)
    }
}
