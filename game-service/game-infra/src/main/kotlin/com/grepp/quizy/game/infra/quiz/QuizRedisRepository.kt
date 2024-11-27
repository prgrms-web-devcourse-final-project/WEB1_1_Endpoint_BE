package com.grepp.quizy.game.infra.quiz

import org.springframework.data.repository.CrudRepository

interface QuizRedisRepository : CrudRepository<QuizRedisEntity, Long> {
}