package com.grepp.quizy.quiz.infra.image.repository

import com.grepp.quizy.quiz.infra.image.entity.QuizImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizImageJpaRepository : JpaRepository<QuizImageEntity, Long> {
}