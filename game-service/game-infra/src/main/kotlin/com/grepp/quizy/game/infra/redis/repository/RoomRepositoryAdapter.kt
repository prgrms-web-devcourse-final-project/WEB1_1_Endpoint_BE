package com.grepp.quizy.game.infra.redis.repository

import com.grepp.quizy.game.domain.Room
import com.grepp.quizy.game.domain.RoomRepository
import com.grepp.quizy.game.infra.redis.entity.RoomRedisEntity
import org.springframework.stereotype.Repository

@Repository
class RoomRepositoryAdapter(
    private val roomRedisRepository: RoomRedisRepository
) : RoomRepository {

    override fun save(room: Room): Room {
        return roomRedisRepository.save(RoomRedisEntity.from(room)).toDomain()
    }
}