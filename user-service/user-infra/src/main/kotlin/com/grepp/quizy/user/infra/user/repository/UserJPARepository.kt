package com.grepp.quizy.user.infra.user.repository

import com.grepp.quizy.user.infra.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJPARepository : JpaRepository<UserEntity, Long> {
    fun existsByUserProfile_Email(email: String): Boolean

    fun findByUserProfile_Email(email: String): UserEntity?
}