package com.grepp.quizy.quiz.infra.user.repository

import com.grepp.quizy.quiz.infra.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun findByIdIn(userIds: List<Long>): List<UserEntity>

}