package com.grepp.quizy.game.domain.exception

import com.grepp.quizy.common.exception.DomainException

sealed class UserException(
    errorCode: UserErrorCode
) : DomainException(errorCode) {
    data object UserNotFoundException : UserException(UserErrorCode.USER_NOT_FOUND)
}