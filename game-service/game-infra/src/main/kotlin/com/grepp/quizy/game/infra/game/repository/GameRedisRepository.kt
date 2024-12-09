package com.grepp.quizy.game.infra.game.repository

import com.grepp.quizy.game.infra.game.entity.GameRedisEntity
import org.springframework.data.repository.CrudRepository

interface GameRedisRepository :
    CrudRepository<GameRedisEntity, Long> {
}
