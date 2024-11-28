package com.grepp.quizy.game.infra.user.repository

import com.grepp.quizy.game.infra.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun findByIdIn(userIds: List<Long>): List<UserEntity>

    @Query("SELECT u.rating FROM UserEntity u WHERE u.id = :id")
    fun findRatingById(id: Long): Int?

}