package com.grepp.quizy.quiz.domain.comment.exception

import com.grepp.quizy.common.exception.BaseErrorCode
import com.grepp.quizy.common.exception.ErrorReason
import com.grepp.quizy.common.exception.ErrorReason.Companion.of

enum class CommentErrorCode(
        private val status: Int,
        private val errorCode: String,
        private val message: String,
) : BaseErrorCode {

    COMMENT_NOT_FOUND_ERROR(404, "COMMENT_404_1", "해당 ID의 댓글이 없습니다");

    override val errorReason: ErrorReason
        get() = of(status, errorCode, message)
}
