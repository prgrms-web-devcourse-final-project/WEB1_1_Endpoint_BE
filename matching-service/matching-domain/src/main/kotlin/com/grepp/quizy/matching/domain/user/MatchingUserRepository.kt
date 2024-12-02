package com.grepp.quizy.matching.domain.user

interface MatchingUserRepository {
    fun findById(id: UserId): MatchingUser?
}