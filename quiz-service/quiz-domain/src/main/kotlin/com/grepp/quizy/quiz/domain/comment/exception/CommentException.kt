package com.grepp.quizy.quiz.domain.comment.exception

import com.grepp.quizy.common.exception.DomainException

sealed class CommentException(errorCode: CommentErrorCode) :
        DomainException(errorCode) {

    data object NoPermission :
            CommentException(CommentErrorCode.NO_PERMISSION_ERROR) {
        private fun readResolve(): Any = NoPermission
    }

    data object NotFound :
            CommentException(
                    CommentErrorCode.COMMENT_NOT_FOUND_ERROR
            ) {
        private fun readResolve(): Any = NotFound
    }
}
