package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class CommentUpdater(
        private val commentRepository: CommentRepository
) {

    fun update(
            userId: UserId,
            comment: Comment,
            content: CommentContent,
    ): Comment =
            commentRepository.save(comment.update(userId, content))
}
