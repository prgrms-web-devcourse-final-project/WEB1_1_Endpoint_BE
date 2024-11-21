package com.grepp.quizy.infra.user.entity

import com.grepp.quizy.domain.user.Role
import com.grepp.quizy.domain.user.User
import com.grepp.quizy.domain.user.UserId
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Long = 0,
        @Embedded private val userProfile: UserProfileVO,
        @Embedded private val provider: ProviderTypeVO,
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private val role: Role = Role.USER,
) {
    fun toDomain(): User {
        return User(
                id = UserId(id),
                userProfile = userProfile.toDomain(),
                provider = provider.toDomain(),
                role = role,
        )
    }

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                    id = user.getId().value,
                    userProfile =
                            UserProfileVO.from(user.getUserProfile()),
                    provider =
                            ProviderTypeVO.from(user.getProvider()),
                    role = user.getRole(),
            )
        }
    }

    fun getId(): Long = id

    fun getUserProfile(): UserProfileVO = userProfile

    fun getProvider(): ProviderTypeVO = provider

    fun getRole(): Role = role
}
