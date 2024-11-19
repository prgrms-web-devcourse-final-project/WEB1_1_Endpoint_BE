package com.grepp.quizy.search.infra.quiz.document

import com.grepp.quizy.search.domain.quiz.OptionNumber
import com.grepp.quizy.search.domain.quiz.QuizId
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "user_answer")
class UserAnswerDocument(
    @Id @Field(type = FieldType.Long)
    val id: Long,

    @Field(type = FieldType.Flattened)
    val answers: MutableMap<Long, Int>,
) {

    fun getOptionNumber(id: QuizId) =
        answers[id.value]?.let { option -> id to OptionNumber(option) }
}