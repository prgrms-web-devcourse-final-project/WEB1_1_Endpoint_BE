package com.grepp.quizy.quiz.domain.user

import java.io.Serializable

data class UserCreatedEvent(
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
) : Serializable {
    fun toDomain() = User(UserId(userId), name, profileImageUrl)
}

data class UserUpdatedEvent(
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
) : Serializable {
    fun toDomain() = User(UserId(userId), name, profileImageUrl)
}

data class UserDeletedEvent(val userId: Long) : Serializable