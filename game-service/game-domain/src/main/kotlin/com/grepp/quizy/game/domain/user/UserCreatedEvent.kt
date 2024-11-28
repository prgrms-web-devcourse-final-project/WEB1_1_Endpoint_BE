package com.grepp.quizy.game.domain.user

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

}