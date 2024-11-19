package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.quiz.domain.quiz.*
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("AB")
class ABTestEntity(
        category: QuizCategory,
        content: String,
        tags: MutableSet<QuizTagEntity>,
        options: MutableList<QuizOptionVO>,
        type: QuizType = QuizType.AB_TEST,
        id: Long = 0L,
) : QuizEntity(category, type, content, tags, options, id) {

    override fun toDomain(): Quiz {
        return ABTest.of(
                content =
                        QuizContent(
                                category = this.category,
                                content = this.content,
                                tags = this.tags.map { it.toDomain() }.toList(),
                                options = this.options.map { it.toDomain() },
                        ),
                id = QuizId(this.id),
                dateTime = QuizDateTime(this.createdAt, this.modifiedAt),
        )
    }

    override fun update(quiz: Quiz): QuizEntity {
        updateContent(quiz.content)
        return this
    }

    companion object {
        fun from(quiz: ABTest): ABTestEntity {
            return ABTestEntity(
                            category = quiz.content.category,
                            content = quiz.content.content,
                            tags =
                                    quiz.content.tags
                                            .map { QuizTagEntity.from(it) }
                                            .toMutableSet(),
                            options =
                                    quiz.content.options
                                            .map { QuizOptionVO.from(it) }
                                            .toMutableList(),
                            id = quiz.id.value,
                    )
                    .apply {
                        this.createdAt = quiz.dateTime.createdAt
                        this.modifiedAt = quiz.dateTime.updatedAt
                    }
        }
    }
}
