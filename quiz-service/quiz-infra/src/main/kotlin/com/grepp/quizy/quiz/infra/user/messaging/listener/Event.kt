package com.grepp.quizy.quiz.infra.user.messaging.listener

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.grepp.quizy.quiz.infra.user.messaging.listener.user.UserEvent
import java.io.Serializable

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "_origin"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = UserEvent::class, name = "user-service")
)
interface Event : Serializable {
    val payload: Map<String, Any>
    val eventType: EventType
    val origin: String
}

inline fun <reified T> Event.getPayloadAs(): T {
    return ObjectMapper().convertValue(payload, T::class.java)
}

interface EventType {
    fun getType(): String
}