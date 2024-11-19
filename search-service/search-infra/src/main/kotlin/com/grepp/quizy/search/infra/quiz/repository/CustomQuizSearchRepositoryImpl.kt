package com.grepp.quizy.search.infra.quiz.repository

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders
import com.grepp.quizy.search.infra.quiz.document.CONTENT_FIELD
import com.grepp.quizy.search.infra.quiz.document.QuizDocument
import com.grepp.quizy.search.infra.quiz.document.TAG_FIELD
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit

class CustomQuizSearchRepositoryImpl(
    private val elasticSearchOperations: ElasticsearchOperations
) : CustomQuizSearchRepository {

    private val keywordSearchFields = listOf(
        "$TAG_FIELD^1.5",
        "$TAG_FIELD.nori^1.5",
        "$TAG_FIELD.ngram^1.5",
        CONTENT_FIELD,
        "$CONTENT_FIELD.nori",
        "$CONTENT_FIELD.ngram"
    )

    override fun search(keyword: String, pageable: Pageable): Slice<QuizDocument> {
        val query = QueryBuilders.multiMatch()
            .query(keyword)
            .fields(keywordSearchFields)
            .build()
            ._toQuery()

        val nativeQuery = NativeQueryBuilder()
            .withQuery(query)
            .withPageable(pageable)
            .build()

        val searchHits = elasticSearchOperations.search(nativeQuery, QuizDocument::class.java)
        val content = searchHits.map(SearchHit<QuizDocument>::getContent).toList()
        val hasNext = hasNext(searchHits.totalHits, pageable)

        return SliceImpl(content, pageable, hasNext)
    }

    override fun searchUserAnswer(userId: Long, quizIds: List<Long>): Map<Long, Int> {
        TODO("Not yet implemented")
    }

    private fun hasNext(totalHits: Long, pageable: Pageable): Boolean =
        totalHits > (pageable.pageNumber + 1) * pageable.pageSize
}