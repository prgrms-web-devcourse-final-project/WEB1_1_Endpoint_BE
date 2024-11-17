package com.grepp.quizy.infra.user.exception

import com.grepp.quizy.common.exception.InfraException
import com.grepp.quizy.domain.user.exception.UserErrorCode

class UserNotFoundException : InfraException(UserErrorCode.USER_NOT_FOUND) {

}