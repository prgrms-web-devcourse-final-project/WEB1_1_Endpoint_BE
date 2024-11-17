package com.grepp.quizy.quiz.infra.quiz.entity

import QuizAnswerVO
import QuizContentVO
import QuizOptionVO
import com.grepp.quizy.quiz.domain.OXQuiz
import com.grepp.quizy.quiz.domain.Quiz
import com.grepp.quizy.quiz.domain.QuizId
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("OX")
class OXQuizEntity(
        content: QuizContentVO,
        tags: Set<QuizTagEntity>,
        options: List<QuizOptionVO>,
        id: Long = 0L,
        val answer: QuizAnswerVO,
) : BaseQuizEntity(content, tags, options, id) {

    override fun toDomain(): Quiz {
        return OXQuiz(
                content = this.content.toDomain(),
                tags = this.tags.map { it.toDomain() },
                options =
                        this.options.map { it.toDomain() },
                answer = answer.toDomain(),
                id = QuizId(this.id),
        )
    }

    companion object {
        fun from(quiz: OXQuiz): OXQuizEntity {
            return OXQuizEntity(
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
