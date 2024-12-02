package com.grepp.quizy.user.domain.user

import java.time.LocalDateTime

class UserCreatedEvent(
    val userId: Long = 0,
    val name: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val provider: AuthProvider = AuthProvider.DEFAULT,
    val providerId: String = "",
    val role: Role = Role.USER
) : UserEvent {
    override val eventType: String = javaClass.simpleName
    override val aggregateType: String = "User"
    override val aggregateId: Long = userId
    override val timestamp: LocalDateTime = LocalDateTime.now()

    override fun toPayload(): Map<String, Any> {
        return mapOf(
            "userId" to userId,
            "name" to name,
            "email" to email,
            "profileImageUrl" to profileImageUrl,
            "provider" to provider,
            "providerId" to providerId,
            "role" to role
        )
    }

    companion object {
        fun from(user: User): UserCreatedEvent {
            return UserCreatedEvent(
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