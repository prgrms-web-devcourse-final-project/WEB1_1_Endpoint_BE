package com.grepp.quizy.game.infra.redis.entity

import com.grepp.quizy.game.domain.GameStatus
import com.grepp.quizy.game.domain.Room
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

private const val HOURS_IN_SECOND = 3600L

@RedisHash("gameRoom")
class RoomRedisEntity(
    @Id
    private val id: Long = 0,

    @TimeToLive
    private val ttl: Long = HOURS_IN_SECOND,

    private val status: GameStatus = GameStatus.WAITING,

    private val playerIds: Set<Long> = setOf()
) {
    companion object {
        fun from(room: Room): RoomRedisEntity {
            return RoomRedisEntity(
                id = room.id,
                playerIds = room.playerIds
            )
        }
    }

    fun toDomain(): Room {
        return Room(
            id = id,
            status = status,
            playerIds = playerIds
        )
    }
}