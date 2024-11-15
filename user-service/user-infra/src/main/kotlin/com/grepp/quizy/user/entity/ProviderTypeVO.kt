package com.grepp.quizy.user.entity

import com.grepp.quizy.user.AuthProvider
import com.grepp.quizy.user.ProviderType
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