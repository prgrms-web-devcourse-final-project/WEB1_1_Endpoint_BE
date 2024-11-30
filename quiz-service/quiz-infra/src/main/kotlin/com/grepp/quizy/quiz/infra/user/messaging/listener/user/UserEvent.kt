package com.grepp.quizy.quiz.infra.user.messaging.listener.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.grepp.quizy.common.NoArg
import com.grepp.quizy.quiz.domain.user.QuizUser
import com.grepp.quizy.quiz.domain.user.UserId
import com.grepp.quizy.quiz.domain.user.UserProfile
import com.grepp.quizy.quiz.infra.user.messaging.listener.Event
import com.grepp.quizy.quiz.infra.user.messaging.listener.EventType

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
    fun toProfile(): UserProfile {
        return UserProfile(
            name = name,
            imgPath = profileImageUrl
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
    fun toProfile(): UserProfile {
        return UserProfile(
            name = name,
            imgPath = profileImageUrl
        )
    }
}
