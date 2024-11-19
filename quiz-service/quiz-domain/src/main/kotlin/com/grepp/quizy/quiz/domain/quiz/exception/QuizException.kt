package com.grepp.quizy.quiz.domain.quiz.exception

import com.grepp.quizy.common.exception.DomainException

sealed class QuizException(errorCode: QuizErrorCode) :
        DomainException(errorCode) {

    data object QuizNotFoundException :
            QuizException(QuizErrorCode.QUIZ_NOT_FOUND_ERROR) {
        private fun readResolve(): Any = QuizNotFoundException
    }
}
