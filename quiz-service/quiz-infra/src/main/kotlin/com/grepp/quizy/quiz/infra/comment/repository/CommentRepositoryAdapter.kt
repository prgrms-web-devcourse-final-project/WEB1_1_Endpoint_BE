package com.grepp.quizy.quiz.infra.comment.repository

import com.grepp.quizy.quiz.domain.comment.Comment
import com.grepp.quizy.quiz.domain.comment.CommentId
import com.grepp.quizy.quiz.domain.comment.CommentRepository
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.user.exception.QuizUserException
import com.grepp.quizy.quiz.infra.comment.entity.CommentEntity
import com.grepp.quizy.quiz.infra.user.repository.QuizUserJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class CommentRepositoryAdapter(
        private val commentJpaRepository: CommentJpaRepository,
        private val quizUserJpaRepository: QuizUserJpaRepository
) : CommentRepository {

    override fun save(comment: Comment): Comment {
        val commentEntity = commentJpaRepository
            .save(CommentEntity.from(comment))
        return commentEntity.toDomain(
            quizUserJpaRepository.findById(commentEntity.writerId)
                .orElseThrow { QuizUserException.NotFound }
        )
    }


    override fun findById(id: CommentId): Comment? =
            commentJpaRepository
                    .findById(id.value)
                    .map {
                        val writer = quizUserJpaRepository
                            .findById(it.writerId)
                            .orElseThrow{ QuizUserException.NotFound }
                        it.toDomain(writer)
                    }
                    .orElse(null)

    override fun findAllByQuizId(quizId: QuizId): List<Comment> {
        val commentEntities = commentJpaRepository.findAllByQuizId(quizId.value)
        val writerEntities = quizUserJpaRepository.findAllById(
                commentEntities.map { it.writerId }
        ).associateBy { it.id }
        return commentEntities.map {
            it.toDomain(writerEntities[it.writerId] ?: throw QuizUserException.NotFound)
        }
    }

    override fun delete(comment: Comment) =
            commentJpaRepository.delete(CommentEntity.from(comment))
}
