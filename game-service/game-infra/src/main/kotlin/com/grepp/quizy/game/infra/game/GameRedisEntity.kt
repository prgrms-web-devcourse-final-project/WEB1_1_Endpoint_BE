package com.grepp.quizy.game.infra.game

import com.grepp.quizy.game.domain.Game
import com.grepp.quizy.game.domain.GameLevel
import com.grepp.quizy.game.domain.GameStatus
import com.grepp.quizy.game.domain.GameSubject
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

private const val HOURS_IN_SECOND = 3600L

@RedisHash
class GameRedisEntity(
    @Id
    val id: Long = 0,

    @TimeToLive
    private val ttl: Long = HOURS_IN_SECOND,

    val subject: GameSubject,

    val quizCount: Int,

    val level: GameLevel,

    val status: GameStatus,

    val playerIds: MutableSet<Long>,

    val inviteCode: String
) {
    companion object {
        fun from(game: Game): GameRedisEntity {
            return GameRedisEntity(
                id = game.id,
                subject = game.subject,
                quizCount = game.quizCount,
                level = game.level,
                status = game.status,
                playerIds = game.playerIds,
                inviteCode = game.inviteCode
            )
        }

    }

    fun toDomain(): Game {
        return Game(
            id = id,
            subject = subject,
            quizCount = quizCount,
            level = level,
            status = status,
            playerIds = playerIds,
            inviteCode = inviteCode
        )
    }


}