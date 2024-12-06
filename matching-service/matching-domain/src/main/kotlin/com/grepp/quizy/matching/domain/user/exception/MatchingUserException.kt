package com.grepp.quizy.matching.domain.user.exception

import com.grepp.quizy.common.exception.DomainException

sealed class MatchingUserException(errorCode: MatchingUserErrorCode) : DomainException(errorCode) {
    data object NotFound : MatchingUserException(MatchingUserErrorCode.USER_NOT_FOUND) {
        private fun readResolve(): Any = NotFound
    }
}