package com.grepp.quizy.quiz.domain.quiz.exception

import com.grepp.quizy.common.exception.DomainException

object QuizNotFoundException :
        DomainException(
                QuizErrorCode.QUIZ_NOT_FOUND_ERROR
        ) {
    private fun readResolve(): Any = QuizNotFoundException
}
