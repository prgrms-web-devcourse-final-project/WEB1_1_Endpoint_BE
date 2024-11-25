package com.grepp.quizy.quiz.infra.quizread.document

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class QuizDocumentWritingConverter<T : QuizDocumentVo> :
        Converter<T, String> {
    override fun convert(source: T): String {
        try {
            val objectMapper = ObjectMapper()
            return objectMapper.writeValueAsString(source)
        } catch (ex: Exception) {
            throw ex
        }
    }
}

@ReadingConverter
class QuizDocumentReadingConverter<T : QuizDocumentVo>(
        private val targetType: Class<T>
) : Converter<String, T> {
    override fun convert(source: String): T? {
        try {
            val objectMapper = ObjectMapper()
            return objectMapper.readValue(source, targetType)
        } catch (ex: Exception) {
            throw ex
        }
    }
}
