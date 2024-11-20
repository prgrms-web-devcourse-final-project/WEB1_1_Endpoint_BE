package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.domain.useranswer.UserId
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("MULTIPLE_CHOICE")
class MultipleChoiceQuizEntity(
        userId: Long,
        category: QuizCategory,
        content: String,
        tags: MutableSet<QuizTagEntity>,
        options: MutableList<QuizOptionVO>,
        var answer: QuizAnswerVO,
        type: QuizType = QuizType.MULTIPLE_CHOICE,
        id: Long = 0L,
) : QuizEntity(userId, category, type, content, tags, options, id) {

    override fun toDomain(): Quiz {
        return MultipleChoiceQuiz.of(
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
                                            it.toDomain()
                                        },
                        ),
                answer = this.answer.toDomain(),
                id = QuizId(this.id),
                dateTime = DateTime(this.createdAt, this.updatedAt),
                commentCount = this.commentCount,
        )
    }

    override fun update(quiz: Quiz): QuizEntity {
        val multipleChoiceQuiz = quiz as MultipleChoiceQuiz
        updateContent(quiz.content)
        commentCount = quiz.commentCount
        answer = QuizAnswerVO.from(multipleChoiceQuiz.answer)
        return this
    }

    companion object {
        fun from(quiz: MultipleChoiceQuiz): MultipleChoiceQuizEntity {
            return MultipleChoiceQuizEntity(
                            userId = quiz.userId.value,
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
                            answer = QuizAnswerVO.from(quiz.answer),
                            id = quiz.id.value,
                    )
                    .apply {
                        this.createdAt = quiz.dateTime.createdAt
                        this.updatedAt = quiz.dateTime.updatedAt
                        this.commentCount = quiz.commentCount
                    }
        }
    }
}
