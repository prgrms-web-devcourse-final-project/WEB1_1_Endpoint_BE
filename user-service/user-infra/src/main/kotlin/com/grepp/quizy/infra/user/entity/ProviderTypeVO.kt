package com.grepp.quizy.infra.user.entity

import com.grepp.quizy.domain.user.AuthProvider
import com.grepp.quizy.domain.user.ProviderType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class ProviderTypeVO (
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: AuthProvider,

    @Column(nullable = false)
    val providerId: String
) {
    fun toDomain(): ProviderType {
        return ProviderType(provider, providerId)
    }

    companion object {
        fun from(providerType: ProviderType): ProviderTypeVO {
            return ProviderTypeVO(providerType.provider, providerType.providerId)
        }
    }
}