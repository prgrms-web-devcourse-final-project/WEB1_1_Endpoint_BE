package com.grepp.quizy.quiz.infra.user.entity

import com.grepp.quizy.quiz.domain.user.QuizUser
import com.grepp.quizy.quiz.domain.user.UserId
import jakarta.persistence.*

@Entity
@Table(name = "users")
class QuizUserEntity(
    @Id
    val id: Long = 0,
    var name: String,
    var imgPath: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_interests",
        joinColumns = [JoinColumn(name = "user_id")]
    )
    val interests: MutableList<QuizUserInterestVO> = mutableListOf()
) {

    companion object {
        fun from(quizUser: QuizUser): QuizUserEntity {
            return QuizUserEntity(
                id = quizUser.id.value,
                name = quizUser.name,
                imgPath = quizUser.imgPath,
                quizUser.interests.map { QuizUserInterestVO(it) }.toMutableList()
            )
        }
    }

    fun toDomain(): QuizUser {
        return QuizUser(UserId(id), name, imgPath)
    }

    fun update(name: String, imgPath: String) {
        this.name = name
        this.imgPath = imgPath
    }
}