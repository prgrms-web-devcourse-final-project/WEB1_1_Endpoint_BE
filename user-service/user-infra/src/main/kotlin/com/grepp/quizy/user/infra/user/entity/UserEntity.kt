package com.grepp.quizy.user.infra.user.entity

import com.grepp.quizy.user.domain.user.Role
import com.grepp.quizy.user.domain.user.User
import com.grepp.quizy.user.domain.user.UserId
import com.grepp.quizy.jpa.BaseTimeEntity
import jakarta.persistence.*

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.USER,

    ) : BaseTimeEntity() {

    constructor() : this(0, UserProfileVO(), ProviderTypeVO(), Role.USER)


    fun toDomain(): User {
        return User(
            id = UserId(id),
            userProfile = userProfile.toDomain(),
            provider = provider.toDomain(),
            _role = role
        )
    }

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id.value,
                userProfile = UserProfileVO.from(user.userProfile),
                provider = ProviderTypeVO.from(user.provider),
                role = user.role
            )
        }
    }
}