package com.grepp.quizy.quiz.infra.log.document

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = "logstash-*")
class LogDocument(
    @Id
    val id: String,

    @Field(type = FieldType.Date)
    val timestamp: LocalDateTime,

    @Field(type = FieldType.Keyword)
    val level: String,

    @Field(type = FieldType.Text)
    val message: String,

    @Field(type = FieldType.Keyword)
    val threadName: String,

    @Field(type = FieldType.Keyword)
    val loggerName: String,

    @Field(type = FieldType.Integer)
    val levelValue: Int,

    @Field(type = FieldType.Keyword)
    val keyword: String?
) {

    companion object {
        const val KEYWORD_FIELD = "keyword"
    }
}