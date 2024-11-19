package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.stereotype.Component

@Component
class CommentAppender(private val commentRepository: CommentRepository) {
    fun append(
            quiz: Quiz,
            writerId: UserId,
            parentCommentId: CommentId,
            content: CommentContent,
    ): Comment {
        val comment =
                Comment(
                        quizId = quiz.id,
                        writerId = writerId,
                        parentCommentId = parentCommentId,
                        _content = content,
                )
        return commentRepository.save(comment)
    }
}
