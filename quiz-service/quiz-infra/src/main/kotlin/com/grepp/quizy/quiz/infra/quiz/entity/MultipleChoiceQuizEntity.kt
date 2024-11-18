package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.quiz.domain.quiz.*
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE")
class MultipleChoiceQuizEntity(
        content: String,
        tags: MutableSet<QuizTagEntity>,
        options: MutableList<QuizOptionVO>,
        var answer: QuizAnswerVO,
        type: QuizType = QuizType.MULTIPLE_CHOICE,
        id: Long = 0L,
) : QuizEntity(type, content, tags, options, id) {

    protected constructor() :
            this(
                    "",
                    mutableSetOf(),
                    mutableListOf(),
                    QuizAnswerVO("", ""),
                    QuizType.MULTIPLE_CHOICE,
                    0L,
            )

    override fun toDomain(): Quiz {
        return MultipleChoiceQuiz.of(
                content =
                        QuizContent(
                                content = this.content,
                                tags =
                                        this.tags
                                                .map {
                                                    it
                                                            .toDomain()
                                                }
                                                .toList(),
                                options =
                                        this.options.map {
                                            it.toDomain()
                                        },
                        ),
                answer = this.answer.toDomain(),
                id = QuizId(this.id),
        )
    }

    override fun update(quiz: Quiz): QuizEntity {
        val multipleChoiceQuiz = quiz as MultipleChoiceQuiz
        updateContent(quiz.content)
        answer =
                QuizAnswerVO.from(multipleChoiceQuiz.answer)
        return this
    }

    companion object {
        fun from(
                quiz: MultipleChoiceQuiz
        ): MultipleChoiceQuizEntity {
            return MultipleChoiceQuizEntity(
                    content = quiz.content.content,
                    tags =
                            quiz.content.tags
                                    .map {
                                        QuizTagEntity.from(
                                                it
                                        )
                                    }
                                    .toMutableSet(),
                    options =
                            quiz.content.options
                                    .map {
                                        QuizOptionVO.from(
                                                it
                                        )
                                    }
                                    .toMutableList(),
                    answer = QuizAnswerVO.from(quiz.answer),
                    id = quiz.id.value,
            )
        }
    }
}
