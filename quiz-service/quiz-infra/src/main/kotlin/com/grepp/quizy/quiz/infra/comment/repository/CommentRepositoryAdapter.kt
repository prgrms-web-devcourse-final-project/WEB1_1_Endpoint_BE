package com.grepp.quizy.quiz.infra.comment.repository

import com.grepp.quizy.quiz.domain.comment.Comment
import com.grepp.quizy.quiz.domain.comment.CommentId
import com.grepp.quizy.quiz.domain.comment.CommentRepository
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.infra.comment.entity.CommentEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class CommentRepositoryAdapter(
        private val commentJpaRepository: CommentJpaRepository
) : CommentRepository {

    override fun save(comment: Comment): Comment =
            commentJpaRepository
                    .save(CommentEntity.from(comment))
                    .toDomain()

    override fun findById(id: CommentId): Comment? =
            commentJpaRepository
                    .findById(id.value)
                    .map { it.toDomain() }
                    .orElse(null)

    override fun findAllByQuizId(quizId: QuizId): List<Comment> =
            commentJpaRepository
                    .findAllByQuizId(quizId.value)
                    .map(CommentEntity::toDomain)

    override fun delete(comment: Comment) =
            commentJpaRepository.delete(CommentEntity.from(comment))
}
