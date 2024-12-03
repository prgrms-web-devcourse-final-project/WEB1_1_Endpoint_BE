package com.grepp.quizy.game.infra.user.messaging.listener.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.grepp.quizy.common.NoArg
import com.grepp.quizy.game.domain.user.User
import com.grepp.quizy.game.infra.user.messaging.listener.Event
import com.grepp.quizy.game.infra.user.messaging.listener.EventType


data class UserEvent(
    @JsonProperty("payload")
    override val payload: Map<String, Any>,
    @JsonProperty("event_type")
    override val eventType: UserEventType,
    @JsonProperty("origin")
    override val origin: String
) : Event {

    enum class UserEventType(
        @JsonValue
        val value: String
    ) : EventType {
        USER_CREATED("UserCreatedEvent"),
        USER_UPDATED("UserUpdatedEvent"),
        USER_DELETED("UserDeletedEvent"),
        ;

        override fun getType(): String {
            return value
        }
    }
}

@NoArg
class UserCreatedEvent(
    val userId: Long,
    val name: String,
    val email: String,
    val profileImageUrl: String,
    val provider: String,
    val providerId: String,
    val role: String
) {
    fun toUser(): User {
        return User(
            id = userId,
            _name = name,
            _imgPath = profileImageUrl
        )
    }
}

@NoArg
class UserDeletedEvent(
    val userId: Long
) {
}

@NoArg
class UserUpdatedEvent(
    val userId: Long,
    val name: String,
    val profileImageUrl: String
) {
    fun toUser(): User {
        return User(
            id = userId,
            _name = name,
            _imgPath = profileImageUrl
        )
    }
}