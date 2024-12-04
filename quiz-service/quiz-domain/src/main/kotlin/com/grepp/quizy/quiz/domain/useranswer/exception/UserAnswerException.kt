package com.grepp.quizy.quiz.domain.useranswer.exception

import com.grepp.quizy.common.exception.DomainException

sealed class UserAnswerException(
    errorCode: UserAnswerErrorCode
) : DomainException(errorCode) {

    data object NotFound : UserAnswerException(UserAnswerErrorCode.USER_ANSWER_NOT_FOUND) {
        private fun readResolve(): Any = NotFound
    }

    data object AlreadyExists : UserAnswerException(UserAnswerErrorCode.USER_ANSWER_ALREADY_EXISTS) {
        private fun readResolve(): Any = AlreadyExists
    }

    data object InvalidUserChoice : UserAnswerException(UserAnswerErrorCode.INVALID_USER_CHOICE) {
        private fun readResolve(): Any = InvalidUserChoice
    }
}