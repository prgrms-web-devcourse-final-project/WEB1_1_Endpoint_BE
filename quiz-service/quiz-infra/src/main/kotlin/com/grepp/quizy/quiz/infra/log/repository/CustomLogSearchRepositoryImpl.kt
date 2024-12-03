package com.grepp.quizy.quiz.infra.log.repository

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders
import co.elastic.clients.json.JsonData
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregation
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.ZoneId

@Repository
class CustomLogSearchRepositoryImpl(
    private val elasticsearchOperations: ElasticsearchOperations
) : CustomLogSearchRepository {

    override fun topKKeyword(k: Int): List<String> {
        val aggregation = Aggregation.of { agg ->
            agg.terms { terms ->
                terms.field("keyword")
                    .size(k)
            }
        }

        val query = NativeQueryBuilder()
            .withQuery(
                QueryBuilders.bool()
                    .must(
                        QueryBuilders.match().query("/api/quiz/search").field("message").build()._toQuery()
                    )
                    .must(rangeQuery())
                    .build()
                    ._toQuery()
            )
            .withAggregation("top_keywords", aggregation)
            .build()

        val result = elasticsearchOperations.search(query, Map::class.java, IndexCoordinates.of("logstash-*"))

        return parseAggregationResult(result)
    }

    private fun rangeQuery(): Query {
        val now = LocalDateTime.now()
        val startOfDay = now.toLocalDate().atStartOfDay()
        val endOfDay = now.toLocalDate().atTime(23, 59, 59, 999000000)

        val startOfDayMillis = startOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfDayMillis = endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        return QueryBuilders.range()
            .field("@timestamp")
            .gte(JsonData.of(startOfDayMillis))
            .lte(JsonData.of(endOfDayMillis))
            .build()
            ._toQuery()
    }

    private fun parseAggregationResult(result: SearchHits<Map<*, *>>): List<String> {
        val aggregationResult = (result.aggregations.aggregations() as? ArrayList<*>)?.get(0) as ElasticsearchAggregation
        return aggregationResult
            .aggregation().aggregate
            .sterms().buckets()
            .array()
            .map { it.key().stringValue() }
    }
}