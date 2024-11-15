package com.grepp.quizy.infra.quiz

import com.grepp.quizy.domain.quiz.*
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*
import java.time.LocalDateTime

@Document(indexName = "quizzes")
@Mapping(mappingPath = "elastic/es-mappings.json")
@Setting(settingPath = "elastic/es-settings.json")
class QuizDocument(
    @Id @Field(type = FieldType.Keyword)
    val id: Long,

    @Field(type = FieldType.Text)
    val question: String,

    @Field(type = FieldType.Text)
    val tag: String,

    @Field(type = FieldType.Text)
    val type: String,

    @Field(type = FieldType.Object)
    val choices: QuizChoice,

    @Field(type = FieldType.Integer)
    val numLikes: Int = 0,

    @Field(type = FieldType.Integer)
    val numComments: Int = 0,

    @Field(
        type = FieldType.Date,
        format = [DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis])
    val createdAt: LocalDateTime,
) {

    fun toDomain() = Quiz(
        id = id,
        question = question,
        tag = QuizTag.from(tag),
        type = QuizType.fromType(type),
        choice = choices,
        like = QuizLike(numLikes),
        comment = QuizComment(numComments),
    )
}