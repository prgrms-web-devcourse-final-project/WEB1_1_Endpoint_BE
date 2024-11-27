package com.grepp.quizy.quiz.domain.user

class User(
    val id: UserId = UserId(),
    private var _name: String,
    private var _imgPath: String,
) {
    val name: String
        get() = _name

    val imgPath: String
        get() = _imgPath

    companion object {
        fun from(event: UserCreatedEvent): User {
            return User(
                id = UserId(event.userId),
                _name = event.name,
                _imgPath = event.profileImageUrl
            )
        }
    }
}