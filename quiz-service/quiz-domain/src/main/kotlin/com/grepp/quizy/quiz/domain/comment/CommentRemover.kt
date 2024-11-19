package com.grepp.quizy.quiz.domain.comment

import org.springframework.stereotype.Component

@Component
class CommentRemover(
        private val commentRepository: CommentRepository
) {

    fun remove(comment: Comment) {
        commentRepository.delete(comment)
    }
}
