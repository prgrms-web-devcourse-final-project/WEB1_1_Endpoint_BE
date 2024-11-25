package com.grepp.quizy.user.domain.user

interface UserLogoutUseCase {
    fun logout(userId: UserId, accessToken: String)
}