package com.grepp.quizy.quiz.infra.quiz.repository

import com.grepp.quizy.quiz.infra.quiz.entity.QuizTagEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuizTagJpaRepository :
        JpaRepository<QuizTagEntity, Long> {

    @Query(
            "SELECT qt FROM QuizTagEntity qt WHERE qt.name IN :name"
    )
    fun findByNameIn(
            name: List<String>
    ): List<QuizTagEntity>
}
