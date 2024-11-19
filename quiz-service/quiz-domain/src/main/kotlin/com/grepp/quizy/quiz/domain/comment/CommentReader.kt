package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.comment.exception.CommentException
import com.grepp.quizy.quiz.domain.quiz.Quiz
import org.springframework.stereotype.Component

@Component
class CommentReader(private val commentRepository: CommentRepository) {

    fun read(id: CommentId): Comment {
        return commentRepository.findById(id) ?: throw CommentException.NotFound
    }

    fun readAll(quiz: Quiz): List<Comment> =
            commentRepository
                    .findAllByQuizId(quiz.id)
                    .groupBy { it.id }
                    .values
                    .flatten()
                    .also { comments ->
                        comments
                                .filter { it.hasParent() }
                                .forEach { comment ->
                                    comments
                                            .find {
                                                it.id == comment.parentCommentId
                                            }
                                            ?.addChildren(comment)
                                }
                    }
                    .filter { !it.hasParent() }
}
