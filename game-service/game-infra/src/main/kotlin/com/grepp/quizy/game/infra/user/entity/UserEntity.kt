package com.grepp.quizy.game.infra.user.entity

import com.grepp.quizy.game.domain.user.User
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "users")
@Entity
class UserEntity(
    @Id
    val id: Long = 0,
    val name: String = "",
    val imgPath: String = "",
    val rating: Int = 1500,
) {
    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                name = user.name,
                imgPath = user.imgPath,
                rating = user.rating
            )
        }
    }

    fun toDomain(): User {
        return User(
            id = id,
            _name = name,
            _imgPath = imgPath,
            _rating = rating
        )
    }
}