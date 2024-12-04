package com.grepp.quizy.user.domain.user

import com.grepp.quizy.common.dto.DateTime
import java.time.LocalDateTime


class User(
    val id: UserId,
    private var _userProfile: UserProfile,
    private var _role: Role = Role.USER,
    val provider: ProviderType,
    val dateTime: DateTime = DateTime.init()
) {
    val role: Role
        get() = _role

    val userProfile: UserProfile
        get() = _userProfile

    fun changeRole() {
        this._role = Role.USER
    }

    fun updateProfile(userProfile: UserProfile) {
        this._userProfile = userProfile
    }

    fun isGuest(): Boolean {
        return this._role == Role.GUEST
    }
}