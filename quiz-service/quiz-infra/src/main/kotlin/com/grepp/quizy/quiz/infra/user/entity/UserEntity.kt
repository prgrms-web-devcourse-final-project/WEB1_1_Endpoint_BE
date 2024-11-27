package com.grepp.quizy.quiz.infra.user.entity

import com.grepp.quizy.quiz.domain.user.User
import com.grepp.quizy.quiz.domain.user.UserId
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class UserEntity(
    @Id
    val id: Long = 0,
    val name: String,
    val imgPath: String,
) {
    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id.value,
                name = user.name,
                imgPath = user.imgPath,
            )
        }
    }

    fun toDomain(): User {
        return User(UserId(id), name, imgPath)
    }
}