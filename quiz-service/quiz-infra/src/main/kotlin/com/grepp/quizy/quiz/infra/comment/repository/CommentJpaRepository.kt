package com.grepp.quizy.quiz.infra.comment.repository

import com.grepp.quizy.quiz.infra.comment.entity.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CommentJpaRepository : JpaRepository<CommentEntity, Long> {

    fun findAllByQuizId(value: Long): List<CommentEntity>
}
