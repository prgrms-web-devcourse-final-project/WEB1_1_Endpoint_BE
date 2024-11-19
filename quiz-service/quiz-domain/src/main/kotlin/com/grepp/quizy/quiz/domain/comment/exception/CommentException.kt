package com.grepp.quizy.quiz.domain.comment.exception

import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.DomainException

sealed class CommentException(errorCode: BaseErrorCode) :
        DomainException(errorCode) {

    data object NotFound :
            CommentException(CommentErrorCode.COMMENT_NOT_FOUND_ERROR) {
        private fun readResolve(): Any = NotFound
    }
}
