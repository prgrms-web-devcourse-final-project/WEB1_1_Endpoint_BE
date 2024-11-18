package com.grepp.quizy.search.infra.quiz

import com.grepp.quizy.search.domain.quiz.QuizType
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*
import java.time.LocalDateTime

const val CONTENT_FIELD = "content"
const val TAG_FIELD = "tag"

@Document(indexName = "quizzes")
@Mapping(mappingPath = "elastic/es-mappings.json")
@Setting(settingPath = "elastic/es-settings.json")
class QuizDocument(
    @Id @Field(type = FieldType.Keyword)
    val id: Long,

    @Field(type = FieldType.Keyword)
    val category: String,

    @Field(type = FieldType.Keyword)
    val type: QuizType,

    @Field(type = FieldType.Text)
    val content: String,

    @Field(type = FieldType.Text)
    val tags: String,

    @Field(type = FieldType.Object)
    val options: List<QuizOptionVO>,

    @Field(type = FieldType.Object)
    val answer: QuizAnswerVO?,

    @Field(
        type = FieldType.Date,
        format = [DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis])
    val createdAt: LocalDateTime,

    @Field(
        type = FieldType.Date,
        format = [DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis])
    val updatedAt: LocalDateTime,
)