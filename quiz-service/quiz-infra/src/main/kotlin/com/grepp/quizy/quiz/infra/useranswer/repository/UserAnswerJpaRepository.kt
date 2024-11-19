package com.grepp.quizy.quiz.infra.useranswer.repository

import com.grepp.quizy.quiz.infra.useranswer.entity.UserAnswerEntity
import com.grepp.quizy.quiz.infra.useranswer.entity.UserAnswerEntityId
import org.springframework.data.jpa.repository.JpaRepository

interface UserAnswerJpaRepository :
        JpaRepository<
                UserAnswerEntity,
                UserAnswerEntityId,
        > {}
