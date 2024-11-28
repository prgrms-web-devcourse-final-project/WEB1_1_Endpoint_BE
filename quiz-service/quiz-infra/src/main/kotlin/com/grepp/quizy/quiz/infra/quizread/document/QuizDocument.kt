package com.grepp.quizy.quiz.infra.quizread.document

import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty
import com.grepp.quizy.quiz.infra.quizread.messaging.listener.QuizCDCEvent
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*
import java.time.Instant
import java.time.ZoneId

@Document(indexName = "quizzes")
@Mapping(mappingPath = "elastic/es-mappings.json")
@Setting(settingPath = "elastic/es-settings.json")
class QuizDocument(
        @Id @Field(type = FieldType.Long) val id: Long,
        @Field(type = FieldType.Long) val userId: Long,
        @Field(type = FieldType.Keyword) val category: QuizCategory,
        @Field(type = FieldType.Keyword) val type: QuizType,
        @Field(type = FieldType.Text) var content: String,
        @Field(type = FieldType.Text) var tags: List<String>,
        @Field(type = FieldType.Nested) var options: List<QuizOptionVO>,
        @Field(type = FieldType.Integer) val totalAnsweredUsers: Int,
        @Field(type = FieldType.Object) var answer: QuizAnswerVO?,
        @Field(type = FieldType.Keyword) val difficulty: QuizDifficulty?,
        @Field(
                type = FieldType.Date,
                format =
                        [
                                DateFormat
                                        .date_hour_minute_second_millis,
                                DateFormat.epoch_millis,
                        ],
        )
        val createdAt: LocalDateTime,
        @Field(
                type = FieldType.Date,
                format =
                        [
                                DateFormat
                                        .date_hour_minute_second_millis,
                                DateFormat.epoch_millis,
                        ],
        )
        var updatedAt: LocalDateTime,
) {
        companion object {
                const val ID_FIELD = "id"
                const val CONTENT_FIELD = "content"
                const val CATEGORY_FIELD = "category.keyword"
                const val TAG_FIELD = "tags"
                const val DIFFICULTY_FIELD = "difficulty.keyword"
                const val TYPE_FIELD = "type.keyword"

                fun from(domain: Quiz): QuizDocument {
                        return QuizDocument(
                                id = domain.id.value,
                                userId = domain.creatorId.value,
                                category = domain.content.category,
                                type = domain.type,
                                content = domain.content.content,
                                tags = domain.content.tags.map { it.name },
                                options = domain.content.options.map { QuizOptionVO.from(it) },
                                totalAnsweredUsers = domain.getTotalAnsweredCount(),
                                answer = when(domain is Answerable) {
                                        true -> QuizAnswerVO(
                                                value = domain.getCorrectAnswer(),
                                                explanation = domain.getAnswerExplanation()
                                        )
                                        false -> null
                                },
                                difficulty = when(domain is Answerable) {
                                        true -> domain.getDifficulty()
                                        false -> null
                                },
                                createdAt = domain.dateTime.createdAt ?: LocalDateTime.now(),
                                updatedAt = domain.dateTime.updatedAt ?: LocalDateTime.now(),
                        )
                }

                fun from(quizCDCEvent: QuizCDCEvent): QuizDocument {
                        return QuizDocument(
                                id = quizCDCEvent.id,
                                userId = quizCDCEvent.userId,
                                category = quizCDCEvent.category,
                                type = quizCDCEvent.type,
                                content = quizCDCEvent.content,
                                tags = emptyList(),
                                options = emptyList(),
                                totalAnsweredUsers = 0,
                                answer = when(quizCDCEvent.answer != null && quizCDCEvent.explanation != null) {
                                        true -> QuizAnswerVO(
                                                value = quizCDCEvent.answer,
                                                explanation = quizCDCEvent.explanation
                                        )
                                        false -> null
                                },
                                difficulty = QuizDifficulty.NONE,
                                createdAt = quizCDCEvent.getCreatedAt(),
                                updatedAt = quizCDCEvent.getUpdatedAt(),
                        )
                }
        }

        fun update(updateQuiz: QuizCDCEvent) {
                if (updateQuiz.answer != null && updateQuiz.explanation != null) {
                        this.answer = QuizAnswerVO(
                                value = updateQuiz.answer,
                                explanation = updateQuiz.explanation
                        )
                }
                this.content = updateQuiz.content
                this.updatedAt = toLocalDateTime(updateQuiz.updatedAt)
        }

        fun addQuizOption(option: QuizOptionVO) {
                this.options += option
        }

        fun removeQuizOption(number: Int) {
                options = options.filterNot { it.optionNumber == number }
        }

        fun addQuizTag(name: String) {
                tags += name
        }

        fun removeQuizTag(name: String) {
                tags = tags.filterNot { it == name }
        }

        private fun toLocalDateTime(epochMillis: Long): LocalDateTime {
                return LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(epochMillis / 1000),
                        ZoneId.systemDefault()
                )
        }
}
