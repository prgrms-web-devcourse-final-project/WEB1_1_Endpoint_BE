package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.user.UserId

interface CommentDeleteUseCase {
    fun deleteComment(commentId: CommentId, userId: UserId)
}
