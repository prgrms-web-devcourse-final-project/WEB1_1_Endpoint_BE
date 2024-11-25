package com.grepp.quizy.game.infra.user.repository

import com.grepp.quizy.game.domain.User
import com.grepp.quizy.game.domain.UserRepository
import com.grepp.quizy.game.infra.user.entity.UserEntity
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdaptor(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun save(user: User): User =
        userJpaRepository.save(UserEntity.from(user))
            .toDomain()

}