package com.grepp.quizy.user.infra.user.entity

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.jpa.BaseTimeEntity
import com.grepp.quizy.user.domain.user.Role
import com.grepp.quizy.user.domain.user.User
import com.grepp.quizy.user.domain.user.UserId
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Embedded
    val userProfile: UserProfileVO,

    @Embedded
    val provider: ProviderTypeVO,

    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.USER
) : BaseTimeEntity(createdAt, updatedAt) {

    fun toDomain(): User {
        return User(
            id = UserId(id),
            _userProfile = userProfile.toDomain(),
            provider = provider.toDomain(),
            _role = role,
            dateTime = DateTime(createdAt, updatedAt)
        )
    }

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id.value,
                userProfile = UserProfileVO.from(user.userProfile),
                provider = ProviderTypeVO.from(user.provider),
                role = user.role,
                createdAt = user.dateTime.createdAt,
                updatedAt = user.dateTime.updatedAt
            )
        }
    }
}