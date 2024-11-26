package com.grepp.quizy.user.domain.user

interface UserUpdateUseCase {
    fun updateProfile(userId: UserId, name: String?, imageFile: ImageFile?)
}