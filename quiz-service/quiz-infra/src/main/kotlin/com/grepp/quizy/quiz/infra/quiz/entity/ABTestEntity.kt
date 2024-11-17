package com.grepp.quizy.quiz.infra.quiz.entity

import QuizContentVO
import QuizOptionVO
import com.grepp.quizy.quiz.domain.ABTest
import com.grepp.quizy.quiz.domain.Quiz
import com.grepp.quizy.quiz.domain.QuizId
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("AB")
class ABTestEntity(
        content: QuizContentVO,
        tags: Set<QuizTagEntity>,
        options: List<QuizOptionVO>,
        id: Long = 0L,
) : BaseQuizEntity(content, tags, options, id) {

    override fun toDomain(): Quiz {
        return ABTest(
                content = this.content.toDomain(),
                tags = this.tags.map { it.toDomain() },
                options =
                        this.options.map { it.toDomain() },
                id = QuizId(this.id),
        )
    }

    companion object {
        fun from(quiz: ABTest): ABTestEntity {
            return ABTestEntity(
                    content =
                            QuizContentVO.from(
                                    quiz.content
                            ),
                    tags =
                            quiz.tags
                                    .map {
                                        QuizTagEntity.from(
                                                it
                                        )
                                    }
                                    .toSet(),
                    options =
                            quiz.options.map {
                                QuizOptionVO.from(it)
                            },
                    id = quiz.id.value,
            )
        }
    }
}
