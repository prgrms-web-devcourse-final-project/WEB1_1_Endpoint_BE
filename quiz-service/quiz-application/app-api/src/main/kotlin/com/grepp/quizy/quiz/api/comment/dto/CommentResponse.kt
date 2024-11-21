package com.grepp.quizy.quiz.api.comment.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.grepp.quizy.quiz.domain.comment.Comment

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CommentResponse(
        val id: Long,
        val quizId: Long,
        val writerId: Long,
        val parentCommentId: Long,
        val content: String,
        val childComments: List<CommentResponse>,
        val createdAt: String,
        val updatedAt: String,
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                    id = comment.id.value,
                    quizId = comment.quizId.value,
                    writerId = comment.writerId.value,
                    parentCommentId = comment.parentCommentId.value,
                    content = comment.content.value,
                    childComments =
                            comment.childComments.map { from(it) },
                    createdAt = comment.dateTime.createdAt.toString(),
                    updatedAt = comment.dateTime.updatedAt.toString(),
            )
        }
    }
}
