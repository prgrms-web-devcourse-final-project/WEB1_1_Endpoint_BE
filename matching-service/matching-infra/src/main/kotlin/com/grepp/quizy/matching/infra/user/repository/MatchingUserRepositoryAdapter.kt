package com.grepp.quizy.matching.infra.user.repository

import com.grepp.quizy.matching.domain.user.MatchingUserRepository
import com.grepp.quizy.matching.domain.user.UserId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MatchingUserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository
) : MatchingUserRepository {

    override fun findById(id: UserId) =
        userJpaRepository.findByIdOrNull(id.value)?.MatchingUser()
}