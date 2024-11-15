package com.grepp.quizy.user

import com.grepp.quizy.common.domain.BaseId

class UserId(
    id: Long = 0
) : BaseId<Long>(id) {
    companion object {
        fun from(id: Long): UserId {
            return UserId(id)
        }
    }
}