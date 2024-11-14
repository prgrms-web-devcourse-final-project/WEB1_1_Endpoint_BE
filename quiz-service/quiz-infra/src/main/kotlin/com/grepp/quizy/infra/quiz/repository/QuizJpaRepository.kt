package com.grepp.quizy.infra.quiz.repository

import com.grepp.quizy.infra.quiz.entity.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizJpaRepository : JpaRepository<QuizEntity, Long>
