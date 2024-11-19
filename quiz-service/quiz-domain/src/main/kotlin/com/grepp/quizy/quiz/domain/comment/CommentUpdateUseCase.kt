package com.grepp.quizy.quiz.domain.comment

interface CommentUpdateUseCase {
    fun updateComment(commentId: CommentId, content: CommentContent): Comment
}
