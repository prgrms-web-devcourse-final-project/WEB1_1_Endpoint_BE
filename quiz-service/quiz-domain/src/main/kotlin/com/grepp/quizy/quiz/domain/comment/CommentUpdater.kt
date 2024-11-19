package com.grepp.quizy.quiz.domain.comment

import org.springframework.stereotype.Component

@Component
class CommentUpdater(private val commentRepository: CommentRepository) {

    fun update(comment: Comment, content: CommentContent): Comment =
            commentRepository.save(comment.update(content))
}
