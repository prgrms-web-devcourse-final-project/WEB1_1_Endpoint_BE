package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.user.UserId

interface CommentUpdateUseCase {
    fun updateComment(
            commentId: CommentId,
            userId: UserId,
            updatedContent: CommentContent,
    ): Comment
}
