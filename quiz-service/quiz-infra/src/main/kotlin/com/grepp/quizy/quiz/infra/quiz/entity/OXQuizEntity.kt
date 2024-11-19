package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.quiz.domain.quiz.*
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("OX")
class OXQuizEntity(
        content: String,
        tags: MutableSet<QuizTagEntity>,
        options: MutableList<QuizOptionVO>,
        id: Long = 0L,
        type: QuizType = QuizType.OX,
        var answer: QuizAnswerVO,
) : QuizEntity(type, content, tags, options, id) {

    protected constructor() :
            this(
                    "",
                    mutableSetOf(),
                    mutableListOf(),
                    0L,
                    QuizType.OX,
                    QuizAnswerVO("", ""),
            )

    override fun toDomain(): Quiz {
        return OXQuiz.of(
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
                answer = answer.toDomain(),
                id = QuizId(this.id),
        )
    }

    override fun update(quiz: Quiz): QuizEntity {
        val oxQuiz = quiz as OXQuiz

        updateContent(quiz.content)
        answer = QuizAnswerVO.from(oxQuiz.answer)
        return this
    }

    companion object {
        fun from(quiz: OXQuiz): OXQuizEntity {
            return OXQuizEntity(
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
