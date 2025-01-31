package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.domain.user.UserId
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
@DiscriminatorValue("AB")
class ABTestEntity(
    userId: Long,
    category: QuizCategory,
    content: String,
    tags: MutableSet<QuizTagEntity>,
    options: MutableList<QuizOptionVO>,
    type: QuizType = QuizType.AB_TEST,
    id: Long = 0L,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) : QuizEntity(userId, category, type, content, tags, options, id, createdAt, updatedAt) {

    override fun toDomain(): Quiz {
        return ABTest.of(
                userId = UserId(this.userId),
                content =
                        QuizContent(
                                category = this.category,
                                content = this.content,
                                tags =
                                        this.tags
                                                .map { it.toDomain() }
                                                .toList(),
                                options =
                                        this.options.map {
                                            it.toDomain(type)
                                        },
                        ),
                id = QuizId(this.quizId),
                dateTime = DateTime(this.createdAt, this.updatedAt),
                commentCount = this.commentCount,
        )
    }

    override fun update(quiz: Quiz): QuizEntity {
        updateContent(quiz.content)
        commentCount = quiz.commentCount
        return this
    }

    companion object {
        fun from(quiz: ABTest): ABTestEntity {
            return ABTestEntity(
                            userId = quiz.creatorId.value,
                            category = quiz.content.category,
                            content = quiz.content.content,
                            tags =
                                    quiz.content.tags
                                            .map {
                                                QuizTagEntity.from(it)
                                            }
                                            .toMutableSet(),
                            options =
                                    quiz.content.options
                                            .map {
                                                QuizOptionVO.from(it)
                                            }
                                            .toMutableList(),
                            id = quiz.id.value,
                            createdAt = quiz.dateTime.createdAt,
                            updatedAt = quiz.dateTime.updatedAt,
                    )
                    .apply {
                        this.commentCount = quiz.commentCount
                    }
        }
    }
}
