package com.grepp.quizy.matching.domain.match

import com.grepp.quizy.matching.domain.user.UserId
import com.grepp.quizy.matching.domain.user.UserVector

data class UserStatus(val userId: UserId, val vector: UserVector)