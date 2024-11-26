package com.grepp.quizy.quiz.domain.image.exception

import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason

enum class QuizImageErrorCode(
    private val status: Int,
    private val errorCode: String,
    private val message: String,
) : BaseErrorCode {
    IMAGE_UPLOAD_ERROR(500, "FILE_500_1", "이미지 업로드 중 오류가 발생했습니다"),
    IMAGE_NOT_FOUND_ERROR(404, "FILE_404_1", "해당 ID의 이미지가 없습니다");

    override val errorReason: ErrorReason
        get() = ErrorReason.of(status, errorCode, message)
}
