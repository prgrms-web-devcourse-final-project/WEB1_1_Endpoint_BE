package com.grepp.quizy.user.domain.user

import java.time.LocalDateTime

class UserUpdatedEvent(
    val userId: Long = 0,
    val name: String = "",
    val profileImageUrl: String = "",
) : UserEvent {
    override val eventType: String = javaClass.simpleName
    override val aggregateType: String = "User"
    override val aggregateId: Long = userId
    override val timestamp: LocalDateTime = LocalDateTime.now()

    override fun toPayload(): Map<String, Any> {
        return mapOf(
            "userId" to userId,
            "name" to name,
            "profileImageUrl" to profileImageUrl,
        )
    }

    companion object {
        fun from(user: User): UserUpdatedEvent {
            return UserUpdatedEvent(
                userId = user.id.value,
                name = user.userProfile.name,
                profileImageUrl = user.userProfile.profileImageUrl,
            )
        }
    }
}