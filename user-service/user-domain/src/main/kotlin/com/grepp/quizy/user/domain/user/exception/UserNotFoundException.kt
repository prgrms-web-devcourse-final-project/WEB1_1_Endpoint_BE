package com.grepp.quizy.user.domain.user.exception

import com.grepp.quizy.common.exception.InfraException
import com.grepp.quizy.user.domain.user.exception.UserErrorCode

class UserNotFoundException : InfraException(UserErrorCode.USER_NOT_FOUND) {

}