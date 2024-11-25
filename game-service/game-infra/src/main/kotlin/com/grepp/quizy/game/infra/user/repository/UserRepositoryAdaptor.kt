package com.grepp.quizy.game.infra.user.repository

import com.grepp.quizy.game.domain.user.User
import com.grepp.quizy.game.domain.user.UserRepository
import com.grepp.quizy.game.infra.user.entity.UserEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdaptor(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun save(user: User): User =
        userJpaRepository.save(UserEntity.from(user))
            .toDomain()

    override fun findById(userId: Long): User? {
        return userJpaRepository.findByIdOrNull(userId)?.toDomain()
    }

    override fun findByIdIn(userIds: List<Long>): List<User> {
        return userJpaRepository.findByIdIn(userIds)
            .map(UserEntity::toDomain)
    }

}