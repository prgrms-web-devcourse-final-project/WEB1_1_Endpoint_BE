package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.quiz.QuizId

interface CommentReadUseCase {
    fun getComments(quizId: QuizId): List<Comment>
}
