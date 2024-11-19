package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.quiz.domain.quiz.QuizTag
import com.grepp.quizy.quiz.domain.quiz.QuizTagId
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
    fun toDomain() = QuizTag.of(name = name, id = QuizTagId(id))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is QuizTagEntity) return false

        if (id != 0L && other.id != 0L) {
            return id == other.id
        }
        return name == other.name
    }

    override fun hashCode(): Int {
        return if (id != 0L) {
            id.hashCode()
        } else {
            name.hashCode()
        }
    }

    companion object {
        fun from(tag: QuizTag): QuizTagEntity {
            return QuizTagEntity(name = tag.name, id = tag.id.value)
        }
    }
}
