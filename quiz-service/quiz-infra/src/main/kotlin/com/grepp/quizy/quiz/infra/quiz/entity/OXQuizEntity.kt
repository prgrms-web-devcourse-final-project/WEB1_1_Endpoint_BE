package com.grepp.quizy.quiz.infra.quiz.entity

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.domain.user.UserId
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
@DiscriminatorValue("OX")
class OXQuizEntity(
    userId: Long,
    category: QuizCategory,
    content: String,
    tags: MutableSet<QuizTagEntity>,
    options: MutableList<QuizOptionVO>,
    id: Long = 0L,
    type: QuizType = QuizType.OX,
    var answer: QuizAnswerVO,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) : QuizEntity(userId, category, type, content, tags, options, id, createdAt, updatedAt) {

    override fun toDomain(): Quiz {
        return OXQuiz.of(
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
                answer = answer.toDomain(),
                id = QuizId(this.quizId),
                dateTime = DateTime(this.createdAt, this.updatedAt),
                commentCount = this.commentCount,
        )
    }

    override fun update(quiz: Quiz): QuizEntity {
        val oxQuiz = quiz as OXQuiz
        commentCount = quiz.commentCount
        updateContent(quiz.content)
        answer = QuizAnswerVO.from(oxQuiz.answer)
        return this
    }

    companion object {
        fun from(quiz: OXQuiz): OXQuizEntity {
            return OXQuizEntity(
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
                            answer = QuizAnswerVO.from(quiz.answer),
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
