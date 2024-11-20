package com.grepp.quizy.quiz.infra.like.entity

import com.grepp.quizy.jpa.BaseTimeEntity
import com.grepp.quizy.quiz.domain.like.Like
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserId
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "likes")
class LikeEntity(@EmbeddedId var id: LikeEntityId) :
        BaseTimeEntity() {

    fun toDomain() =
            Like(
                    likerId = UserId(id.userId),
                    quizId = QuizId(id.quizId),
            )

    companion object {
        fun from(domain: Like): LikeEntity {
            return LikeEntity(
                    id =
                            LikeEntityId(
                                    userId = domain.likerId.value,
                                    quizId = domain.quizId.value,
                            )
            )
        }
    }
}
