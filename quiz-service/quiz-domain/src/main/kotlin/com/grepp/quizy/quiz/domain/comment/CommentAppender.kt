package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.stereotype.Component

@Component
class CommentAppender(
        private val commentRepository: CommentRepository
) {
    fun append(
            quizId: QuizId,
            writerId: UserId,
            parentCommentId: CommentId,
            content: CommentContent,
    ): Comment {
        val comment =
                Comment(
                        quizId = quizId,
                        writerId = writerId,
                        parentCommentId = parentCommentId,
                        _content = content,
                )
        return commentRepository.save(comment)
    }
}
