package com.grepp.quizy.quiz.infra.quizread.document

import com.grepp.quizy.quiz.domain.quiz.QuizOption
import com.grepp.quizy.quiz.domain.quizread.QuizSortType
import com.grepp.quizy.quiz.infra.quizread.messaging.listener.cdc.QuizOptionCDCEvent
import kotlinx.serialization.Serializable
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

sealed interface QuizDocumentVo

@Serializable
data class QuizOptionVO(
        @Field(type = FieldType.Integer)
        val optionNumber: Int,
        @Field(type = FieldType.Text)
        val content: String,
        @Field(type = FieldType.Integer)
        val selectionCount: Int = 0,
        @Field(type = FieldType.Long)
        val imageId: Long? = null,
) : QuizDocumentVo {

        constructor() : this(0, "", 0)

        companion object {
                fun from(domain: QuizOption): QuizOptionVO {
                        when (domain) {
                                is QuizOption.ABTestOption -> {
                                        return QuizOptionVO(
                                                domain.optionNumber,
                                                domain.content,
                                                domain.selectionCount,
                                                domain.imageId?.value
                                        )
                                }
                                is QuizOption.MultipleChoiceOption -> {
                                        return QuizOptionVO(
                                                domain.optionNumber,
                                                domain.content,
                                                domain.selectionCount
                                        )
                                }
                                is QuizOption.OXOption -> {
                                        return QuizOptionVO(
                                                domain.optionNumber,
                                                domain.content,
                                                domain.selectionCount
                                        )
                                }
                        }
                }

                fun from(event: QuizOptionCDCEvent): QuizOptionVO {
                        return QuizOptionVO(
                                event.optionNumber,
                                event.content,
                                event.selectionCount,
                                event.imageId
                        )
                }
        }
}

@Serializable
data class QuizAnswerVO(val value: String, val explanation: String) :
        QuizDocumentVo


enum class SortField(val fieldName: String) {
        TRENDING("totalAnsweredUser"),
        NEW("createdAt");

        companion object {
                fun from(type: QuizSortType) = when (type) {
                        QuizSortType.TRENDING -> TRENDING
                        QuizSortType.NEW -> NEW
                }
        }
}
