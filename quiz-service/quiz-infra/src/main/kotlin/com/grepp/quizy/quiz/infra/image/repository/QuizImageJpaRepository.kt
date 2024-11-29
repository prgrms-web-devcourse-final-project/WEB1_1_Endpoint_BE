package com.grepp.quizy.quiz.infra.image.repository

import com.grepp.quizy.quiz.infra.image.entity.QuizImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuizImageJpaRepository : JpaRepository<QuizImageEntity, Long> {

    @Query("DELETE FROM QuizImageEntity q WHERE q.id IN :ids")
    fun deleteByIdIn(ids: List<Long>)
}