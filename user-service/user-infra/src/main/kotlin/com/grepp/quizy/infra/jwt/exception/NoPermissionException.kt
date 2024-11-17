package com.grepp.quizy.infra.jwt.exception

import com.grepp.quizy.common.exception.GlobalErrorCode
import com.grepp.quizy.common.exception.WebException

class NoPermissionException private constructor() : WebException(GlobalErrorCode.PERMISSION_DENIED) {

}