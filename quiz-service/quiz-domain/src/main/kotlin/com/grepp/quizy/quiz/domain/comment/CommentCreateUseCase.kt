package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserId

interface CommentCreateUseCase {
    fun createComment(
            quizId: QuizId,
            writerId: UserId,
            parentCommentId: CommentId,
            content: CommentContent,
    ): Comment
}
