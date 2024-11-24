package com.grepp.quizy.quiz.infra.quizread.document

import com.grepp.quizy.quiz.domain.quizread.OptionNumber
import com.grepp.quizy.quiz.domain.quizread.QuizId
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "user_answer")
class UserAnswerDocument(
        @Id @Field(type = FieldType.Long) val id: Long,
        @Field(type = FieldType.Flattened)
        val answers: MutableMap<Long, Int>,
) {

    fun getOptionNumber(id: QuizId) =
            answers[id.value]?.let { option ->
                id to OptionNumber(option)
            }
}
