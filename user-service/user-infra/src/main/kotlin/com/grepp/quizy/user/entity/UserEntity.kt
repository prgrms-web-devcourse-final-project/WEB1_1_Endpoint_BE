package com.grepp.quizy.user.entity

import com.grepp.quizy.user.Role
import com.grepp.quizy.user.User
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Embedded
    val userProfileVO: UserProfileVO,

    @Embedded
    val provider: ProviderTypeVO,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.USER,

    ) {
    fun toDomain(): User {
        return User(
            id = id,
            userProfile = userProfileVO.toDomain(),
            provider = provider.toDomain(),
            role = role
        )
    }

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                userProfileVO = UserProfileVO.from(user.userProfile),
                provider = ProviderTypeVO.from(user.provider),
                role = user.role
            )
        }
    }
}