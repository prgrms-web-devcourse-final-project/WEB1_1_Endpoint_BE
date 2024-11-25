package com.grepp.quizy.game.domain.exception

import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason

enum class UserErrorCode(
    private val status: Int,
    private val errorCode: String,
    private val message: String,
) : BaseErrorCode {

    USER_NOT_FOUND(
        404,
        "G-U001",
        "사용자를 찾을 수 없습니다."
    ),

    ;

    override val errorReason: ErrorReason
        get() = ErrorReason.of(status, errorCode, message)
}