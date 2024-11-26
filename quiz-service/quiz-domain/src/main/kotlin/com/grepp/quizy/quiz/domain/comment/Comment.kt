package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.comment.exception.CommentException
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId

class Comment(
        val quizId: QuizId,
        val writerId: UserId,
        val parentCommentId: CommentId,
        private var _content: CommentContent,
        val id: CommentId = CommentId(0),
        private val _childComments: MutableList<Comment> =
                mutableListOf(),
        val dateTime: DateTime = DateTime.init(),
) {

    val content: CommentContent
        get() = _content

    val childComments: List<Comment>
        get() = _childComments.toList()

    fun addChildren(comment: Comment) {
        _childComments.add(comment)
    }

    fun hasParent(): Boolean {
        return parentCommentId != CommentId(0)
    }

    fun update(userId: UserId, content: CommentContent): Comment {
        validateOwner(userId)
        _content = content
        return this
    }

    fun validateOwner(userId: UserId) {
        if (writerId != userId) {
            throw CommentException.NoPermission
        }
    }
}
