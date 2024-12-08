package com.grepp.quizy.quiz.api.comment.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.grepp.quizy.quiz.domain.comment.Comment
import com.grepp.quizy.quiz.domain.comment.Writer
import com.grepp.quizy.quiz.domain.user.QuizUser

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CommentResponse(
        val id: Long,
        val quizId: Long,
        val writer: WriterResponse,
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
                    writer = WriterResponse.from(comment.writer),
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

data  class WriterResponse(
    val id: Long,
    val name: String,
    val profileImageUrl: String
) {
    companion object {
        fun from(writer: Writer): WriterResponse {
            return WriterResponse(
                id = writer.id.value,
                name = writer.name,
                profileImageUrl = writer.profileImageUrl
            )
        }
    }
}
