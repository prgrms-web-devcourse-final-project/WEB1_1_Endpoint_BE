package com.grepp.quizy.quiz.domain.comment

import com.grepp.quizy.quiz.domain.quiz.QuizId

interface CommentRepository {

    fun save(comment: Comment): Comment

    fun findById(id: CommentId): Comment?

    fun findAllByQuizId(quizId: QuizId): List<Comment>

    fun delete(comment: Comment)
}
