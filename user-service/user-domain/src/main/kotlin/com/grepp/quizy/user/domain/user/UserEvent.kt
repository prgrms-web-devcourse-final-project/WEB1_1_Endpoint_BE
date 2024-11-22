package com.grepp.quizy.user.domain.user

import java.io.Serializable

interface UserEvent : Serializable {
    fun getUserId(): Long
}