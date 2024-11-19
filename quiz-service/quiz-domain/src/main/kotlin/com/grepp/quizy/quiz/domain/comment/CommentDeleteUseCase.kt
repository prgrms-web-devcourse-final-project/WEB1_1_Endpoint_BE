package com.grepp.quizy.quiz.domain.comment

interface CommentDeleteUseCase {
    fun deleteComment(commentId: CommentId)
}
