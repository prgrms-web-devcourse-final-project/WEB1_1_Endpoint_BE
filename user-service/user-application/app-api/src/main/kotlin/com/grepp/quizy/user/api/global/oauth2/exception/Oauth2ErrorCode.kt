package com.grepp.quizy.user.api.global.oauth2.exception

import com.grepp.quizy.common.constants.QuizyConstants.BAD_REQUEST
import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason

enum class Oauth2ErrorCode(
    private val status: Int,
    private val errorCode: String,
    private val message: String,
) : BaseErrorCode {

    UNSUPPORTED_PROVIDER(BAD_REQUEST, "OAUTH2_400", "지원하지 않는 제공자"),

    ;


    override val errorReason: ErrorReason
        get() = ErrorReason(status, errorCode, message)
}