package com.grepp.quizy.game.infra.game

import com.grepp.quizy.game.domain.*
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

    val setting: GameSetting,

    val status: GameStatus,

    val players: Players,

    val inviteCode: InviteCode
) {
    companion object {
        fun from(game: Game): GameRedisEntity {
            return GameRedisEntity(
                id = game.id,
                setting = game.setting,
                status = game.status,
                players = game.players,
                inviteCode = game.inviteCode
            )
        }

    }

    fun toDomain(): Game {
        return Game(
            id = id,
            _setting = setting,
            _status = status,
            _players = players,
            inviteCode = inviteCode
        )
    }


}