package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.user.UserId
import com.grepp.quizy.matching.user.UserVector

data class UserStatus(val userId: UserId, val vector: UserVector) {
}