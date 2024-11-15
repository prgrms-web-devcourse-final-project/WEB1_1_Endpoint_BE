package com.grepp.quizy.domain.user

import java.time.LocalDateTime


class User (
    val id: UserId,
    val userProfile: UserProfile,
    val role: Role = Role.USER,
    val provider: ProviderType,
) {
}