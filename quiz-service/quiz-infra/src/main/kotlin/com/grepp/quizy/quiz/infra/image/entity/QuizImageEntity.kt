package com.grepp.quizy.quiz.infra.image.entity

import com.grepp.quizy.quiz.domain.image.QuizImage
import jakarta.persistence.*

@Entity
@Table(name = "quiz_image")
class QuizImageEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val url: String
) {

    companion object {
        fun from(domain: QuizImage): QuizImageEntity{
            return QuizImageEntity(
                id = domain.id,
                url = domain.url
            )
        }
    }

    fun toDomain(): QuizImage {
        return QuizImage(
            id = id,
            url = url
        )
    }

}