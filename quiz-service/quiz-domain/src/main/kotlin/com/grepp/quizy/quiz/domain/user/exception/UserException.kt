package com.grepp.quizy.quiz.domain.user.exception

import com.grepp.quizy.common.exception.DomainException

sealed class QuizUserException(errorCode: QuizUserErrorCode) : DomainException(errorCode) {
    data object NotFound : QuizUserException(QuizUserErrorCode.USER_NOT_FOUND) {
        private fun readResolve(): Any = NotFound
    }
}