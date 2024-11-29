package com.grepp.quizy.matching.infra.feign.exception

import com.grepp.quizy.common.exception.GlobalErrorCode
import com.grepp.quizy.common.exception.InfraException

object OtherServerBadRequestException :
    InfraException(GlobalErrorCode.OTHER_SERVER_BAD_REQUEST) {
    private fun readResolve(): Any = OtherServerBadRequestException
}