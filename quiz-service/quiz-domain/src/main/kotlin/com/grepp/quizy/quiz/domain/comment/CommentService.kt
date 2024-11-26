package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
        private val commentReader: CommentReader,
        private val commentAppender: CommentAppender,
        private val commentUpdater: CommentUpdater,
        private val commentRemover: CommentRemover,
) :
        CommentCreateUseCase,
        CommentReadUseCase,
        CommentUpdateUseCase,
        CommentDeleteUseCase {

    @Transactional
    override fun createComment(
            quizId: QuizId,
            writerId: UserId,
            parentCommentId: CommentId,
            content: CommentContent,
    ): Comment {
        return commentAppender.append(
                quizId,
                writerId,
                parentCommentId,
                content,
        )
    }

    override fun getComments(quizId: QuizId): List<Comment> {
        return commentReader.readAll(quizId)
    }

    override fun updateComment(
            commentId: CommentId,
            userId: UserId,
            updatedContent: CommentContent,
    ): Comment {
        val comment = commentReader.read(commentId)
        return commentUpdater.update(userId, comment, updatedContent)
    }

    override fun deleteComment(commentId: CommentId, userId: UserId) {
        val comment = commentReader.read(commentId)
        comment.validateOwner(userId)
        commentRemover.remove(comment)
    }
}
