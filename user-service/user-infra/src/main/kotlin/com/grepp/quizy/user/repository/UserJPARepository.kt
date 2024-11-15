package com.grepp.quizy.user.repository

import com.grepp.quizy.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJPARepository : JpaRepository<UserEntity, Long> {
    fun existsByUserProfileVO_Email(email: String): Boolean
}