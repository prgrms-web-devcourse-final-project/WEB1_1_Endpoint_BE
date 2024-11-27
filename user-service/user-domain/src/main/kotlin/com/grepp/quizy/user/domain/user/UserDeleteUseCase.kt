package com.grepp.quizy.user.domain.user

interface UserDeleteUseCase {
    fun removeUser(userId: UserId)
}