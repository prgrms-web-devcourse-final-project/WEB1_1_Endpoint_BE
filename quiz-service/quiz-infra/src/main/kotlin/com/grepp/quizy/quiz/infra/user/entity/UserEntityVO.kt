package com.grepp.quizy.quiz.infra.user.entity

import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class QuizUserInterestVO(
    @Enumerated(EnumType.STRING)
    val interest: QuizCategory
)