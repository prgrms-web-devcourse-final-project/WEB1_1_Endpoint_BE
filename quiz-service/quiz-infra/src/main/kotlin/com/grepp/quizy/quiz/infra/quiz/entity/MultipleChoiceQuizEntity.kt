package com.grepp.quizy.quiz.infra.quiz.entity

import QuizAnswerVO
import QuizContentVO
import QuizOptionVO
import com.grepp.quizy.quiz.domain.*
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import org.springframework.data.jpa.domain.AbstractPersistable_.id

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE")
class MultipleChoiceQuizEntity(
        content: QuizContentVO,
        tags: Set<QuizTagEntity>,
        options: List<QuizOptionVO>,
        val answer: QuizAnswerVO,
        id: Long = 0L,
) : BaseQuizEntity(content, tags, options, id) {

    override fun toDomain(): Quiz {
        return MultipleChoiceQuiz(
                content = this.content.toDomain(),
                tags = this.tags.map { it.toDomain() },
                options =
                        this.options.map { it.toDomain() },
                answer = this.answer.toDomain(),
                id = QuizId(this.id),
        )
    }

    companion object {
        fun from(
                quiz: MultipleChoiceQuiz
        ): MultipleChoiceQuizEntity {
            return MultipleChoiceQuizEntity(
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
                    answer =
                            QuizAnswerVO.from(
                                    quiz.getAnswer()
                            ),
                    id = quiz.id.value,
            )
        }
    }
}
