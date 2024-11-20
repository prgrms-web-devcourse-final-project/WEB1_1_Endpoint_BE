package com.grepp.quizy.user.infra.user.repository

import com.grepp.quizy.user.domain.user.User
import com.grepp.quizy.user.domain.user.UserRepository
import com.grepp.quizy.user.infra.user.entity.UserEntity
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class UserRepositoryAdaptor(
    private val userJPARepository: UserJPARepository
) : UserRepository {

    override fun findById(id: Long): User? =
        userJPARepository.findById(id).getOrNull()?.toDomain()

    override fun findByEmail(email: String): User? =
        userJPARepository.findByUserProfile_Email(email)?.toDomain()


    override fun existsByEmail(email: String): Boolean =
        userJPARepository.existsByUserProfile_Email(email)


    override fun save(user: User): User =
        userJPARepository.save(UserEntity.from(user)).toDomain()


    override fun delete(user: User) =
        userJPARepository.delete(UserEntity.from(user))

}