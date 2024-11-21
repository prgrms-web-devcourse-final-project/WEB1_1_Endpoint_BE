package com.grepp.quizy.infra.user.entity

import com.grepp.quizy.domain.user.UserProfile
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class UserProfileVO(
        @Column(nullable = false) val name: String,
        @Column(nullable = false, unique = true) val email: String,
        @Column(nullable = false) val profileImageUrl: String,
) {
    fun toDomain(): UserProfile {
        return UserProfile(
                name = name,
                email = email,
                profileImageUrl = profileImageUrl,
        )
    }

    companion object {
        fun from(userProfile: UserProfile): UserProfileVO {
            return UserProfileVO(
                    name = userProfile.name,
                    email = userProfile.email,
                    profileImageUrl = userProfile.profileImageUrl,
            )
        }
    }
}
