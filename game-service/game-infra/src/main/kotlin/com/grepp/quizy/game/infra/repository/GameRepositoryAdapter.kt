package com.grepp.quizy.game.infra.repository

import com.grepp.quizy.game.domain.Game
import com.grepp.quizy.game.domain.GameRepository
import com.grepp.quizy.game.infra.redis.entity.GameRedisEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class GameRepositoryAdapter(
    private val gameRedisRepository: GameRedisRepository
) : GameRepository {

    override fun save(game: Game): Game {
        return gameRedisRepository.save(GameRedisEntity.from(game)).toDomain()
    }

    override fun findById(id: Long): Game? {
        return gameRedisRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun findByInviteCode(code: String): Game? {
        return gameRedisRepository.findTopByInviteCode(code)?.toDomain()
    }

}