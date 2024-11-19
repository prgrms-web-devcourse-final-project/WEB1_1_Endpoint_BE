package com.grepp.quizy.domain.user


class User(
    val id: UserId,
    val userProfile: UserProfile,
    val role: Role = Role.USER,
    val provider: ProviderType,
) {
}