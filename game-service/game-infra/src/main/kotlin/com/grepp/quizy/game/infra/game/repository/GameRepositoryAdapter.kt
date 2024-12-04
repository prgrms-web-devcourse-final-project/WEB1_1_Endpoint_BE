package com.grepp.quizy.game.infra.game.repository

import com.grepp.quizy.game.domain.game.Game
import com.grepp.quizy.game.domain.game.GameRepository
import com.grepp.quizy.game.infra.game.entity.GameRedisEntity
import org.springframework.data.redis.connection.DataType
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class GameRepositoryAdapter(
    private val gameRedisRepository: GameRedisRepository,
    private val redisTemplate: RedisTemplate<String, String>
) : GameRepository {

    override fun save(game: Game): Game {
        return gameRedisRepository
            .save(GameRedisEntity.from(game))
            .toDomain()
    }

    override fun findById(id: Long): Game? {
        return gameRedisRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun findByInviteCode(code: String): Game? {
        return redisTemplate.connectionFactory?.connection.use { connection ->
            val scanner = connection?.scan(
                ScanOptions.scanOptions()
                    .match("game:[0-9]*")
                    .count(100)
                    .build()
            )

            while (scanner!!.hasNext()) {
                val key = String(scanner.next())

                val type = redisTemplate.type(key)
                if (type != DataType.HASH) {
                    continue
                }
                val inviteCode = redisTemplate.opsForHash<String, String>()
                    .get(key, "inviteCode")

                if (inviteCode == code) {
                    return@use gameRedisRepository.findByIdOrNull(key.split(":")[1].toLong())
                        ?.toDomain()
                }
            }
            null
        }
    }

    override fun delete(game: Game) {
        val key = "game:${game.id}*"

        redisTemplate.connectionFactory?.connection.use { connection ->
            val scanner = connection?.scan(
                ScanOptions.scanOptions()
                    .match(key)
                    .count(100)
                    .build()
            )

            while (scanner!!.hasNext()) {
                val candidateKey = String(scanner.next())
                redisTemplate.delete(candidateKey)
            }
        }
    }

}
