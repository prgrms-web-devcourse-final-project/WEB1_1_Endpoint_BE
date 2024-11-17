package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.quiz.domain.QuizTag
import com.grepp.quizy.quiz.domain.QuizTagId
import jakarta.persistence.*

@Entity
@Table(
        name = "quiz_tags",
        uniqueConstraints =
                [
                        UniqueConstraint(
                                name = "uk_tags_name",
                                columnNames = ["name"],
                        )
                ],
)
class QuizTagEntity(
        @Column(nullable = false) val name: String,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,
) {
    fun toDomain() =
            QuizTag(name = name, id = QuizTagId(id))

    companion object {
        fun from(tag: QuizTag): QuizTagEntity {
            return QuizTagEntity(
                    name = tag.name,
                    id = tag.id.value,
            )
        }
    }
}
