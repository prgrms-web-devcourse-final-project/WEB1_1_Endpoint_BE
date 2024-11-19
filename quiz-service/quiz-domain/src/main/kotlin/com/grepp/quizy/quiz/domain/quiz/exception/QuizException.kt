package com.grepp.quizy.quiz.domain.quiz.exception

import com.grepp.quizy.common.exception.DomainException

sealed class QuizException(errorCode: QuizErrorCode) :
        DomainException(errorCode) {

    data object NotFound :
            QuizException(QuizErrorCode.QUIZ_NOT_FOUND_ERROR) {
        private fun readResolve(): Any = NotFound
    }

    data object NoPermission :
            QuizException(QuizErrorCode.NO_PERMISSION_ERROR) {
        private fun readResolve(): Any = NoPermission
    }
}
