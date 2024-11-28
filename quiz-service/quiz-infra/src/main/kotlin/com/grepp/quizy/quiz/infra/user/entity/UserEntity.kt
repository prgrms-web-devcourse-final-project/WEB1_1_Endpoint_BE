package com.grepp.quizy.quiz.infra.user.entity

import com.grepp.quizy.quiz.domain.user.User
import com.grepp.quizy.quiz.domain.user.UserId
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class UserEntity(
    @Id
    val id: Long = 0,
    private var _name: String,
    private var _imgPath: String,
) {
    val name: String
        get() = _name

    val imgPath: String
        get() = _imgPath

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                id = user.id.value,
                _name = user.name,
                _imgPath = user.imgPath,
            )
        }
    }

    fun toDomain(): User {
        return User(UserId(id), name, imgPath)
    }

    fun update(name: String, imgPath: String) {
        _name = name
        _imgPath = imgPath
    }
}