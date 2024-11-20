package com.grepp.quizy.infra.game.repository

import com.grepp.quizy.domain.game.Room
import com.grepp.quizy.domain.game.RoomRepository
import com.grepp.quizy.infra.game.redis.entity.RoomRedisEntity

class RoomRepositoryAdapter(
    private val roomRedisRepository: RoomRedisRepository
) : RoomRepository {

    override fun save(room: Room): Room {
        return roomRedisRepository.save(RoomRedisEntity.from(room)).toDomain()
    }
}