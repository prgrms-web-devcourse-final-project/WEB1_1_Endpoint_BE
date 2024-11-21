package com.grepp.quizy.domain.user

class User(
        private val id: UserId,
        private val userProfile: UserProfile,
        private val role: Role = Role.USER,
        private val provider: ProviderType,
) {
    fun getId(): UserId = id

    fun getUserProfile(): UserProfile = userProfile

    fun getRole(): Role = role

    fun getProvider(): ProviderType = provider
}
