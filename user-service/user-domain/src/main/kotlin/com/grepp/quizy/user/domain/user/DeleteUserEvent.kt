package com.grepp.quizy.user.domain.user

class DeleteUserEvent(
    private val userId: Long = 0,
) : UserEvent {
    override fun getUserId(): Long {
        return userId
    }

    companion object {
        fun from(user: User): DeleteUserEvent {
            return DeleteUserEvent(user.id.value)
        }
    }

}