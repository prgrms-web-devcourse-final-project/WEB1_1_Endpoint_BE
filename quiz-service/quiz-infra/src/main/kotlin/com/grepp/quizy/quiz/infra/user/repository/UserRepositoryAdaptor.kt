package com.grepp.quizy.quiz.infra.user.repository

import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdaptor(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun findByIdIn(userIds: List<UserId>) =
        userJpaRepository.findAllById(userIds.map { it.value })
            .map { it.toDomain() }
}