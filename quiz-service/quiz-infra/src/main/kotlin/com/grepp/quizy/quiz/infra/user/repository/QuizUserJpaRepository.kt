package com.grepp.quizy.quiz.infra.user.repository

import com.grepp.quizy.quiz.infra.user.entity.QuizUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizUserJpaRepository : JpaRepository<QuizUserEntity, Long>