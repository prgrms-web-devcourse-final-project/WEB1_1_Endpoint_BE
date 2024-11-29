package com.grepp.quizy.user.domain.user

class UpdateUserEvent(
    private val userId: Long = 0,
    val name: String = "",
    val profileImageUrl: String = "",
) : UserEvent {
    override fun getUserId(): Long {
        return userId
    }

    companion object {
        fun from(user: User): UpdateUserEvent {
            return UpdateUserEvent(
                userId = user.id.value,
                name = user.userProfile.name,
                profileImageUrl = user.userProfile.profileImageUrl,
            )
        }
    }
}