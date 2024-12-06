package com.grepp.quizy.matching.domain.user

import com.grepp.quizy.matching.domain.user.exception.MatchingUserException
import org.springframework.stereotype.Component

@Component
class MatchingUserReader(
    private val matchingUserRepository: MatchingUserRepository
) {

    fun read(id: UserId): MatchingUser =
        matchingUserRepository.findById(id) ?: throw MatchingUserException.NotFound
}