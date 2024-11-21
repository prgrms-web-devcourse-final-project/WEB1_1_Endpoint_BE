package com.grepp.quizy.search.infra.quiz

import com.grepp.quizy.search.domain.quiz.*
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*

@Document(indexName = "quizzes")
@Mapping(mappingPath = "elastic/es-mappings.json")
@Setting(settingPath = "elastic/es-settings.json")
class QuizDocument(
        @Id @Field(type = FieldType.Keyword) private val id: Long,
        @Field(type = FieldType.Text) private val question: String,
        @Field(type = FieldType.Text) private val tag: String,
        @Field(type = FieldType.Text) private val type: String,
        @Field(type = FieldType.Object)
        private val choices: QuizChoice,
        @Field(type = FieldType.Integer)
        private val numLikes: Int = 0,
        @Field(type = FieldType.Integer)
        private val numComments: Int = 0,
        @Field(
                type = FieldType.Date,
                format =
                        [
                                DateFormat
                                        .date_hour_minute_second_millis,
                                DateFormat.epoch_millis,
                        ],
        )
        private val createdAt: LocalDateTime,
) {

    fun toDomain() =
            Quiz(
                    id = id,
                    question = question,
                    tag = QuizTag.from(tag),
                    type = QuizType.fromType(type),
                    choice = choices,
                    like = QuizLikeCount(numLikes),
                    comment = QuizCommentCount(numComments),
            )
}
