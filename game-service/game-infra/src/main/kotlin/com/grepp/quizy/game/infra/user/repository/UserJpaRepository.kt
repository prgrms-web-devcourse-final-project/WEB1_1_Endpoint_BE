package com.grepp.quizy.game.infra.user.repository

import com.grepp.quizy.game.infra.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun findByIdIn(userIds: List<Long>): List<UserEntity>

}