package com.grepp.quizy.quiz.infra.image.exception

import com.grepp.quizy.common.exception.InfraException
import com.grepp.quizy.quiz.domain.image.exception.QuizImageErrorCode

sealed class QuizImageInfraException(
    errorCode: QuizImageErrorCode
) : InfraException(errorCode) {

    data object UploadError :
        QuizImageInfraException(QuizImageErrorCode.IMAGE_UPLOAD_ERROR) {
        private fun readResolve(): Any = UploadError
    }
}