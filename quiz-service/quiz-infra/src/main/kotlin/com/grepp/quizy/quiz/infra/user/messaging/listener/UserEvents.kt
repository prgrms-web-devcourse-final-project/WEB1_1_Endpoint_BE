package com.grepp.quizy.quiz.infra.user.messaging.listener

import com.grepp.quizy.quiz.infra.user.entity.UserEntity
import java.io.Serializable

class UserCreatedEvent(
    val userId: Long = 0,
    val name: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val provider: String = "",
    val providerId: String = "",
    val role: String = ""
) : Serializable {
    fun UserEntity() = UserEntity(userId, name, profileImageUrl)
}

class UserUpdatedEvent(
    val userId: Long = 0,
    val name: String = "",
    val profileImageUrl: String = "",
) : Serializable

class UserDeletedEvent(val userId: Long = 0) : Serializable