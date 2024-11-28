package com.grepp.quizy.user.domain.user

class CreateUserEvent(
    private val userId: Long = 0,
    val name: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val provider: AuthProvider = AuthProvider.DEFAULT,
    val providerId: String = "",
    val role: Role = Role.USER
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