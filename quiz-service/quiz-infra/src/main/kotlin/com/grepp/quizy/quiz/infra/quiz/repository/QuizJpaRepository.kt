package com.grepp.quizy.quiz.infra.quiz.repository

import com.grepp.quizy.quiz.infra.quiz.entity.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizJpaRepository :
        JpaRepository<QuizEntity, Long>
