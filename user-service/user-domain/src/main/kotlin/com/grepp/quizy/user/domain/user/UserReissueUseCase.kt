package com.grepp.quizy.user.domain.user

interface UserReissueUseCase {
    fun reissue(userId: UserId): ReissueToken
}