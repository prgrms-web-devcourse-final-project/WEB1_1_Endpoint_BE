package com.grepp.quizy.infra.user.repository

import com.grepp.quizy.infra.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJPARepository : JpaRepository<UserEntity, Long> {
    fun existsByUserProfile_Email(email: String): Boolean
}