package com.grepp.quizy.quiz.infra.user.messaging.listener.kafka

import com.grepp.quizy.quiz.infra.user.entity.UserEntity
import java.io.Serializable

data class UserCreatedEvent(
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
) : Serializable {
    fun UserEntity() = UserEntity(userId, name, profileImageUrl)
}

data class UserUpdatedEvent(
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
) : Serializable

data class UserDeletedEvent(val userId: Long) : Serializable