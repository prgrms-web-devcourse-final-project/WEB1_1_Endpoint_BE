package com.grepp.quizy.game.infra.game.repository

import com.grepp.quizy.game.infra.redis.entity.RoomRedisEntity
import org.springframework.data.repository.CrudRepository

interface RoomRedisRepository : CrudRepository<RoomRedisEntity, Long>