package com.grepp.quizy.user.domain.user

interface UserReadUseCase {
    fun getUser(userId: UserId): User
}