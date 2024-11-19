package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.quiz.QuizReader
import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.stereotype.Service

@Service
class CommentService(
        private val commentReader: CommentReader,
        private val commentAppender: CommentAppender,
        private val commentUpdater: CommentUpdater,
        private val commentRemover: CommentRemover,
        private val quizReader: QuizReader,
) :
        CommentCreateUseCase,
        CommentReadUseCase,
        CommentUpdateUseCase,
        CommentDeleteUseCase {

    override fun createComment(
            quizId: QuizId,
            writerId: UserId,
            parentCommentId: CommentId,
            content: CommentContent,
    ): Comment {
        val quiz = quizReader.read(quizId)
        return commentAppender.append(quiz, writerId, parentCommentId, content)
    }

    override fun getComments(quizId: QuizId): List<Comment> {
        val quiz = quizReader.read(quizId)
        return commentReader.readAll(quiz)
    }

    override fun updateComment(
            commentId: CommentId,
            content: CommentContent,
    ): Comment {
        val comment = commentReader.read(commentId)
        return commentUpdater.update(comment, content)
    }

    override fun deleteComment(commentId: CommentId) {
        val comment = commentReader.read(commentId)
        commentRemover.remove(comment)
    }
}
