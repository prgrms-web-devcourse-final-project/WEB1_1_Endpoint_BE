package com.grepp.quizy.search.infra.quiz

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders
import org.springframework.data.domain.PageRequest
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
            .withPageable(increasePageSize(pageable))
            .build()

        val documents = elasticSearchOperations
            .search(nativeQuery, QuizDocument::class.java)
            .map(SearchHit<QuizDocument>::getContent)
            .toMutableList()

        val hasNext = documents.size > pageable.pageSize
        if (hasNext) documents.removeAt(pageable.pageSize)

        return SliceImpl(documents, pageable, hasNext)
    }

    override fun searchUserAnswer(userId: Long, quizIds: List<Long>): Map<Long, Int> {
        TODO("Not yet implemented")
    }

    private fun increasePageSize(pageable: Pageable) =
        PageRequest.of(pageable.pageNumber, pageable.pageSize + 1, pageable.sort)
}