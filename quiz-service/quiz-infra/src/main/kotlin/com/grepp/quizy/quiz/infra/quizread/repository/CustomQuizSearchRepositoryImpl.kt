package com.grepp.quizy.quiz.infra.quizread.repository

import co.elastic.clients.elasticsearch._types.FieldValue
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField
import com.grepp.quizy.quiz.domain.quiz.QuizDifficulty
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument.Companion.CATEGORY_FIELD
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument.Companion.CONTENT_FIELD
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument.Companion.DIFFICULTY_FIELD
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument.Companion.ID_FIELD
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument.Companion.TAG_FIELD
import com.grepp.quizy.quiz.infra.quizread.document.QuizDocument.Companion.TYPE_FIELD
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHit

class CustomQuizSearchRepositoryImpl(
        private val elasticSearchOperations: ElasticsearchOperations
) : CustomQuizSearchRepository {

    private val keywordSearchFields =
            listOf(
                    "$TAG_FIELD^1.5",
                    "$TAG_FIELD.nori^1.5",
                    "$TAG_FIELD.ngram^1.5",
                    CONTENT_FIELD,
                    "$CONTENT_FIELD.nori",
                    "$CONTENT_FIELD.ngram",
            )

    override fun search(
            keyword: String,
            pageable: Pageable,
    ): Slice<QuizDocument> {
        val query =
                QueryBuilders.multiMatch()
                        .query(keyword)
                        .fields(keywordSearchFields)
                        .build()
                        ._toQuery()

        val nativeQuery =
                NativeQueryBuilder()
                        .withQuery(query)
                        .withPageable(pageable)
                        .build()

        return convertToSlice(nativeQuery, pageable)
    }

    override fun searchNotIn(keyword: String, pageable: Pageable, quizIds: List<Long>): Slice<QuizDocument> {
        val query = QueryBuilders.bool()
            .must(
                QueryBuilders.multiMatch()
                    .query(keyword)
                    .fields(keywordSearchFields)
                    .build()
                    ._toQuery()
            )
            .mustNot(
                QueryBuilders.terms().field(ID_FIELD).terms(TermsQueryField.of { tqf ->
                    tqf.value(quizIds.map { FieldValue.of(it) })
                })
                    .build()
                    ._toQuery()
            )
            .build()
            ._toQuery()

        val nativeQuery =
            NativeQueryBuilder()
                .withQuery(query)
                .withPageable(pageable)
                .build()

        return convertToSlice(nativeQuery, pageable)
    }

    override fun searchAnswerableQuiz(category: String, difficulty: QuizDifficulty, pageable: Pageable): List<QuizDocument> {
        val mustQueries = mutableListOf(QueryBuilders.term().field(CATEGORY_FIELD).value(category).build()._toQuery())

        if (difficulty != QuizDifficulty.RANDOM) {
            mustQueries.add(
                QueryBuilders.term().field(DIFFICULTY_FIELD).value(difficulty.name).build()._toQuery()
            )
        }

        val query = NativeQueryBuilder()
            .withQuery(
                QueryBuilders.bool()
                    .must(mustQueries)
                    .mustNot(
                        QueryBuilders.term().field(TYPE_FIELD).value("AB").build()._toQuery()
                    )
                    .build()
                    ._toQuery()
            )
            .withPageable(pageable)
            .build()

        return elasticSearchOperations.search(query, QuizDocument::class.java)
            .map(SearchHit<QuizDocument>::getContent)
            .toList()
    }

    override fun searchUserAnswer(
            userId: Long,
            quizIds: List<Long>,
    ): Map<Long, Int> {
        TODO("Not yet implemented")
    }

    private fun convertToSlice(nativeQuery: NativeQuery, pageable: Pageable): Slice<QuizDocument> {
        val searchHits =
            elasticSearchOperations.search(
                nativeQuery,
                QuizDocument::class.java,
            )
        val content =
            searchHits
                .map(SearchHit<QuizDocument>::getContent)
                .toList()
        val hasNext = hasNext(searchHits.totalHits, pageable)

        return SliceImpl(content, pageable, hasNext)
    }

    private fun hasNext(
            totalHits: Long,
            pageable: Pageable,
    ): Boolean =
            totalHits > (pageable.pageNumber + 1) * pageable.pageSize
}
