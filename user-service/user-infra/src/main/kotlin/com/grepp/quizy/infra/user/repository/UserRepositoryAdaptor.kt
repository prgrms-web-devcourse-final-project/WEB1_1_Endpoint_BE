package com.grepp.quizy.infra.user.repository

import com.grepp.quizy.domain.user.User
import com.grepp.quizy.domain.user.UserRepository
import com.grepp.quizy.infra.user.entity.UserEntity
import com.grepp.quizy.infra.user.exception.UserNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class UserRepositoryAdaptor (
    private val userJPARepository: UserJPARepository
) : UserRepository {

    override fun findById(id: Long): User {
        return userJPARepository.findById(id).getOrNull()?.toDomain() ?: throw UserNotFoundException()
    }

    override fun existsByEmail(email: String): Boolean {
        return userJPARepository.existsByUserProfile_Email(email)
    }

    override fun save(user: User): User {
        return userJPARepository.save(UserEntity.from(user)).toDomain()
    }

    override fun delete(user: User) {
        userJPARepository.delete(UserEntity.from(user))
    }
}