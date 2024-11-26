package com.grepp.quizy.user.domain.user

interface UserValidUseCase {
    fun isValidUser(userId: UserId): Boolean
}