package com.grepp.quizy.infra.game.repository

import com.grepp.quizy.infra.game.redis.entity.RoomRedisEntity
import org.springframework.data.repository.CrudRepository

interface RoomRedisRepository : CrudRepository<RoomRedisEntity, String>