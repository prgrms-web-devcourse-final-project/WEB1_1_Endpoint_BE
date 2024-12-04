package com.grepp.quizy.matching.infra.user.entity

import com.grepp.quizy.matching.domain.user.InterestCategory
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class MatchingUserInterestVO(
    @Enumerated(EnumType.STRING)
    val interest: InterestCategory
)