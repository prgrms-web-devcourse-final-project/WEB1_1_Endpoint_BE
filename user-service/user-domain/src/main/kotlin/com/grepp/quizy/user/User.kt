package com.grepp.quizy.user

import java.time.LocalDateTime


class User (
    val id: Long = 0, // TODO : UserId로 변경
    val userProfile: UserProfile,
    val role: Role = Role.USER,
    val provider: ProviderType,
) {
}