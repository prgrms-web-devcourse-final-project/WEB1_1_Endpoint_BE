package com.grepp.quizy.quiz.domain.quiz.exception

import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason
import com.grepp.quizy.common.exception.ErrorReason.Companion.of

enum class QuizErrorCode(
        private val status: Int,
        private val errorCode: String,
        private val message: String,
) : BaseErrorCode {

    QUIZ_NOT_FOUND_ERROR(404, "QUIZ_404_1", "해당 ID의 퀴즈가 없습니다");

    override val errorReason: ErrorReason
        get() = of(status, errorCode, message)
}
