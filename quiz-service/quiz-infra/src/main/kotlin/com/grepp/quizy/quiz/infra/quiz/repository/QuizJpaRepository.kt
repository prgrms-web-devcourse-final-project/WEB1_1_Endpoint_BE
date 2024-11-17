package com.grepp.quizy.quiz.infra.quiz.repository

import com.grepp.quizy.quiz.infra.quiz.entity.BaseQuizEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizJpaRepository :
        JpaRepository<BaseQuizEntity, Long>
