package com.grepp.quizy.user.infra.user.entity

import com.grepp.quizy.user.domain.user.AuthProvider
import com.grepp.quizy.user.domain.user.ProviderType
import com.grepp.quizy.user.domain.user.UserProfile
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class ProviderTypeVO(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: AuthProvider,

    @Column(nullable = false)
    val providerId: String
) {
    constructor() : this(AuthProvider.DEFAULT, "")

    fun toDomain(): ProviderType {
        return ProviderType(provider, providerId)
    }

    companion object {
        fun from(providerType: ProviderType): ProviderTypeVO {
            return ProviderTypeVO(providerType.provider, providerType.providerId)
        }
    }
}

@Embeddable
data class UserProfileVO(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val profileImageUrl: String,
) {
    constructor() : this("", "", "")

    fun toDomain(): UserProfile {
        return UserProfile(
            name = name,
            email = email,
            profileImageUrl = profileImageUrl
        )
    }

    companion object {
        fun from(userProfile: UserProfile): UserProfileVO {
            return UserProfileVO(
                name = userProfile.name,
                email = userProfile.email,
                profileImageUrl = userProfile.profileImageUrl
            )
        }
    }
}