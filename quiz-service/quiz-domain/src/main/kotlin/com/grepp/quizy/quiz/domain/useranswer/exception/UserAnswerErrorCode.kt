package com.grepp.quizy.quiz.domain.useranswer.exception

import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason

enum class UserAnswerErrorCode(
    private val status: Int,
    private val errorCode: String,
    private val message: String,
) : BaseErrorCode {

    USER_ANSWER_NOT_FOUND(
        404,
        "Q-UA001",
        "사용자 답변을 찾을 수 없습니다."
    ),

    USER_ANSWER_ALREADY_EXISTS(
        400,
        "Q-UA002",
        "이미 사용자 답변이 존재합니다."
    ),

    INVALID_USER_CHOICE(
        400,
        "Q-UA003",
        "사용자 응답이 유효하지 않습니다."
    );

    override val errorReason: ErrorReason
        get() = ErrorReason.of(status, errorCode, message)
}