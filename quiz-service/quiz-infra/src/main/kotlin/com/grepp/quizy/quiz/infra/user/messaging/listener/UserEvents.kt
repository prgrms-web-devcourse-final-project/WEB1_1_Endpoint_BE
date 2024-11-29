package com.grepp.quizy.quiz.infra.user.messaging.listener

import com.grepp.quizy.common.NoArg
import com.grepp.quizy.quiz.infra.user.entity.UserEntity
import java.io.Serializable

sealed class UserEvent {

    @NoArg
    data class Created(
        val userId: Long,
        val name: String,
        val profileImageUrl: String
    ) : UserEvent() {
        fun toEntity() = UserEntity(userId, name, profileImageUrl)
    }

    @NoArg
    data class Updated(
        val userId: Long,
        val name: String,
        val profileImageUrl: String
    ) : UserEvent()

    @NoArg
    data class Deleted(val userId: Long) : UserEvent()
}