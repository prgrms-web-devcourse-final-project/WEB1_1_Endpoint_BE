package com.grepp.quizy.matching.infra.user.repository

import com.grepp.quizy.matching.infra.user.entity.MatchingUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<MatchingUserEntity, Long>