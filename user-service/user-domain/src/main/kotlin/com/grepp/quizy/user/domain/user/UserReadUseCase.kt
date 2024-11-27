package com.grepp.quizy.user.domain.user

interface UserReadUseCase {
    suspend fun getUserInfo(userId: UserId): UserInfo
}