package com.grepp.quizy.game.infra.redis.entity

import com.grepp.quizy.game.domain.GameStatus
import com.grepp.quizy.game.domain.Room
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.*

private const val HOURS_IN_SECOND = 3600L

@RedisHash("gameRoom")
class RoomRedisEntity(
    @Id
    private val id: String? = null,

    @TimeToLive
    private val ttl: Long = HOURS_IN_SECOND,

    private val status: GameStatus = GameStatus.WAITING,

    private val playerIds: MutableSet<Long> = mutableSetOf()
) {
    companion object {
        fun from(room: Room): RoomRedisEntity {
            return RoomRedisEntity(
                id = UUID.randomUUID().toString(),
                playerIds = room.players.toMutableSet()
            )
        }
    }

    fun toDomain(): Room {
        return Room(
            id = id!!,
            status = status,
            playerIds = playerIds
        )
    }
}