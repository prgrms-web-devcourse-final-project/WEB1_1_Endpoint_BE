package com.grepp.quizy.quiz.infra.user.repository

import com.grepp.quizy.quiz.domain.user.User
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.user.UserRepository
import com.grepp.quizy.quiz.infra.user.entity.UserEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdaptor(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun save(user: User): User =
        userJpaRepository.save(UserEntity.from(user))
            .toDomain()

    override fun findById(userId: UserId): User? {
        return userJpaRepository.findByIdOrNull(userId.value)?.toDomain()
    }

    override fun findByIdIn(userIds: List<UserId>): List<User> {
        return userJpaRepository.findByIdIn(userIds.map { it.value })
            .map(UserEntity::toDomain)
    }

    override fun delete(user: User) {
        userJpaRepository.deleteById(user.id.value)
    }
}