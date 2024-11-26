package com.grepp.quizy.user.domain.user.exception

import com.grepp.quizy.common.exception.DomainException

sealed class CustomUserException(errorCode: UserErrorCode) : DomainException(errorCode) {

    data object DuplicateLoginException :
        CustomUserException(UserErrorCode.ALREADY_LOGGED_IN) {
        private fun readResolve(): Any = DuplicateLoginException

        val EXCEPTION: CustomUserException = DuplicateLoginException
    }

    data object UserNotFoundException :
        CustomUserException(UserErrorCode.USER_NOT_FOUND) {
        private fun readResolve(): Any = UserNotFoundException

        val EXCEPTION: CustomUserException = UserNotFoundException
    }
}