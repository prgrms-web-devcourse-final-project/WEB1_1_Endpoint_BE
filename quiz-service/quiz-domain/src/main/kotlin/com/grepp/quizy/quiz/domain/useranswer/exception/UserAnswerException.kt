package com.grepp.quizy.quiz.domain.useranswer.exception

import com.grepp.quizy.common.exception.DomainException

sealed class UserAnswerException(
    errorCode: UserAnswerErrorCode
) : DomainException(errorCode) {

    data object NotFound : UserAnswerException(UserAnswerErrorCode.USER_ANSWER_NOT_FOUND) {
        private fun readResolve(): Any = NotFound
    }
}