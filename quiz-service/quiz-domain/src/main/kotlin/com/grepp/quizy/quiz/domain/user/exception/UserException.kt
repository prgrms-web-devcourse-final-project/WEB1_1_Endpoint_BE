package com.grepp.quizy.quiz.domain.user.exception

import com.grepp.quizy.common.exception.DomainException

object UserNotFoundException : DomainException(UserErrorCode.USER_NOT_FOUND) {
    private fun readResolve(): Any = UserNotFoundException
}