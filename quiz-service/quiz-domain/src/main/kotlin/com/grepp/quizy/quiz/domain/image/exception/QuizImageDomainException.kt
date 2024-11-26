package com.grepp.quizy.quiz.domain.image.exception

import com.grepp.quizy.common.exception.DomainException

sealed class QuizImageDomainException(
    errorCode: QuizImageErrorCode
) : DomainException(errorCode) {

    data object NotFound : QuizImageDomainException(QuizImageErrorCode.IMAGE_NOT_FOUND_ERROR) {
        private fun readResolve(): Any = NotFound
    }
}