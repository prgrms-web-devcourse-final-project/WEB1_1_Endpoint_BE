package com.grepp.quizy.game.infra.repository

import com.grepp.quizy.game.infra.redis.entity.GameRedisEntity
import org.springframework.data.repository.CrudRepository

interface GameRedisRepository : CrudRepository<GameRedisEntity, Long>