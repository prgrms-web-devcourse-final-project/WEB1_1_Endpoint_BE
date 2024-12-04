package com.grepp.quizy.quiz.infra.user.cache

import org.springframework.data.repository.CrudRepository

interface QuizUserRedisRepository : CrudRepository<QuizUserRedisEntity, Long> {
}