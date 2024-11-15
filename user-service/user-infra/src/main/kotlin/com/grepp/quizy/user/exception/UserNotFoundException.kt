package com.grepp.quizy.user.exception

import com.grepp.quizy.common.exception.InfraException

class UserNotFoundException : InfraException(UserErrorCode.USER_NOT_FOUND) {

}