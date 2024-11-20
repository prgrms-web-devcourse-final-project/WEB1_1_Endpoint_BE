package com.grepp.quizy.quiz.infra.like.repository

import com.grepp.quizy.quiz.infra.like.entity.LikeEntity
import com.grepp.quizy.quiz.infra.like.entity.LikeEntityId
import org.springframework.data.jpa.repository.JpaRepository

interface LikeJpaRepository :
        JpaRepository<LikeEntity, LikeEntityId> {}
