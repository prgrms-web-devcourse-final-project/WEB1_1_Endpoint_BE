package com.grepp.quizy.quiz.infra.user.entity

import com.grepp.quizy.quiz.domain.user.User
import com.grepp.quizy.quiz.domain.user.UserId
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    val id: Long = 0,
    var name: String,
    var imgPath: String,
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

    fun update(name: String, imgPath: String) {
        this.name = name
        this.imgPath = imgPath
    }
}