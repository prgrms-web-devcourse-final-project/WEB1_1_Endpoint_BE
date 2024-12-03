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
    );

    override val errorReason: ErrorReason
        get() = ErrorReason.of(status, errorCode, message)
}