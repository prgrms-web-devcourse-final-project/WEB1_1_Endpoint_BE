package com.grepp.quizy.quiz.api.comment.dto

import com.grepp.quizy.quiz.domain.comment.CommentContent
import com.grepp.quizy.quiz.domain.comment.CommentId
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId

data class CreateCommentRequest(
        val quizId: Long,
        val parentCommentId: Long?,
        val content: String,
) {

    fun toContent(): CommentContent = CommentContent(content)

    fun toParentCommentId(): CommentId =
            parentCommentId?.let { CommentId(it) } ?: CommentId(0)

    fun toQuizId(): QuizId = QuizId(quizId)
}
