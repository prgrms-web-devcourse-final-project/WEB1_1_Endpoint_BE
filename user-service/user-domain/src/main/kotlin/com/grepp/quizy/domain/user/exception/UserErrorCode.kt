package com.grepp.quizy.domain.user.exception

import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason

enum class UserErrorCode (
    private val status: Int,
    private val errorCode: String,
    private val message: String
) : BaseErrorCode {
    USER_NOT_FOUND(404, "U001", "해당 유저를 찾지 못했습니다."),
    USER_ALREADY_EXISTS(409, "U002", "이미 존재하는 유저입니다."),
    ;

    override val errorReason: ErrorReason
        get() = ErrorReason(status, errorCode, message)
}