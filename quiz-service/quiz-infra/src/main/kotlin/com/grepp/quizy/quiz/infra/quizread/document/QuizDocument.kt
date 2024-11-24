package com.grepp.quizy.quiz.infra.quizread.document

import com.grepp.quizy.quiz.domain.quizread.QuizType
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.*

@Document(indexName = "quizzes")
@Mapping(mappingPath = "elastic/es-mappings.json")
@Setting(settingPath = "elastic/es-settings.json")
class QuizDocument(
        @Id @Field(type = FieldType.Long) val id: Long,
        @Field(type = FieldType.Keyword) val category: String,
        @Field(type = FieldType.Keyword) val type: QuizType,
        @Field(type = FieldType.Text) val content: String,
        @Field(type = FieldType.Text) val tags: List<String>,
        @Field(type = FieldType.Object)
        val options: List<QuizOptionVO>,
        @Field(type = FieldType.Object) val answer: QuizAnswerVO?,
        @Field(type = FieldType.Flattened)
        val selectionPerOption: Map<Int, Int>,
        @Field(type = FieldType.Integer) val totalAnsweredUser: Int,
        @Field(type = FieldType.Double) val difficulty: String,
        @Field(type = FieldType.Integer) val totalLikeCount: Int,
        @Field(type = FieldType.Integer) val totalCommentCount: Int,
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
        val updatedAt: LocalDateTime,
) {
        companion object {
                const val CONTENT_FIELD = "content"
                const val CATEGORY_FIELD = "category.keyword"
                const val TAG_FIELD = "tags"
                const val DIFFICULTY_FIELD = "difficulty.keyword"
                const val TYPE_FIELD = "type.keyword"
        }
}
