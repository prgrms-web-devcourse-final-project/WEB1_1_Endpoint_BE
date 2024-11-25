package com.grepp.quizy.user.domain.user

class CreateUserEvent(
    private val userId: Long,
    val name: String,
    val email: String,
    val profileImageUrl: String,
    val provider: AuthProvider,
    val providerId: String,
    val role: Role
) : UserEvent {
    override fun getUserId(): Long {
        return userId
    }

    companion object {
        fun from(user: User): CreateUserEvent {
            return CreateUserEvent(
                userId = user.id.value,
                name = user.userProfile.name,
                email = user.userProfile.email,
                profileImageUrl = user.userProfile.profileImageUrl,
                provider = user.provider.provider,
                providerId = user.provider.providerId,
                role = user.role
            )
        }
    }
}