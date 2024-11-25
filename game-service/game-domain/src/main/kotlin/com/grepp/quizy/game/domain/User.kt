package com.grepp.quizy.game.domain

class User(
    val id: Long = 0,
    val name: String,
    val imgPath: String,
    val rating: Int = 1500,
) {
    companion object {
        fun from(event: UserCreatedEvent): User {
            return User(
                id = event.userId,
                name = event.name,
                imgPath = event.profileImageUrl
            )
        }
    }
}