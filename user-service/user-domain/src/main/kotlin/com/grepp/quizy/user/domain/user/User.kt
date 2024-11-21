package com.grepp.quizy.user.domain.user


class User(
    val id: UserId,
    val userProfile: UserProfile,
    private var _role: Role = Role.USER,
    val provider: ProviderType,
) {
    val role: Role
        get() = _role

    fun changeRole(){
        this._role = Role.USER
    }

    fun isGuest(): Boolean {
        return this._role == Role.GUEST
    }
}