package com.grepp.quizy.user.domain.user

import java.time.LocalDateTime

class UserDeletedEvent(
    val userId: Long = 0,
) : UserEvent {
    override val eventType: String = javaClass.simpleName
    override val aggregateType: String = "User"
    override val aggregateId: Long = userId
    override val timestamp: LocalDateTime = LocalDateTime.now()

    override fun toPayload(): Map<String, Any> {
        return mapOf(
            "userId" to userId
        )
    }

    companion object {
        fun from(user: User): UserDeletedEvent {
            return UserDeletedEvent(user.id.value)
        }
    }

}