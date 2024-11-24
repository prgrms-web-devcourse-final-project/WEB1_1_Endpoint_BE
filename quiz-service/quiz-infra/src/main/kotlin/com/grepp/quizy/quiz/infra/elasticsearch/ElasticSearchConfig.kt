package com.grepp.quizy.quiz.infra.elasticsearch

import com.grepp.quizy.quiz.infra.quizread.document.QuizAnswerVO
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocumentReadingConverter
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocumentWritingConverter
import com.grepp.quizy.quiz.infra.quizread.document.QuizOptionVO
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories(
        basePackages = ["com.grepp.quizy.quiz.infra"]
)
class ElasticSearchConfig : ElasticsearchConfiguration() {

    @Value("\${elasticsearch.host}") private lateinit var host: String

    override fun clientConfiguration(): ClientConfiguration =
            ClientConfiguration.builder().connectedTo(host).build()

    override fun elasticsearchCustomConversions():
            ElasticsearchCustomConversions {
        return ElasticsearchCustomConversions(
                listOf(
                        QuizDocumentWritingConverter<QuizOptionVO>(),
                        QuizDocumentReadingConverter(
                                QuizOptionVO::class.java
                        ),
                        QuizDocumentWritingConverter<QuizAnswerVO>(),
                        QuizDocumentReadingConverter(
                                QuizAnswerVO::class.java
                        ),
                )
        )
    }
}