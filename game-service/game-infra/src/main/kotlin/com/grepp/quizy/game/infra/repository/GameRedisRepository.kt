package com.grepp.quizy.game.infra.repository

import com.grepp.quizy.game.infra.game.GameRedisEntity
import org.springframework.data.repository.CrudRepository

interface GameRedisRepository : CrudRepository<GameRedisEntity, Long> {

    fun findTopByInviteCode(inviteCode: String): GameRedisEntity?

}