package com.grepp.quizy.user.api.user.dto

import com.grepp.quizy.user.domain.user.AuthProvider
import com.grepp.quizy.user.domain.user.User

data class UserResponse(
    val id: Long,
    val email: String,
    val name: String,
    val profileImageUrl: String,
    val provider: AuthProvider,
    val role: String
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id.value,
                email = user.userProfile.email,
                name = user.userProfile.name,
                profileImageUrl = user.userProfile.profileImageUrl,
                provider = user.provider.provider,
                role = user.role.name
            )
        }
    }
}