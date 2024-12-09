package com.grepp.quizy.matching.infra.match.repository

import com.grepp.quizy.matching.domain.match.MatchingPoolRepository
import com.grepp.quizy.matching.domain.match.UserStatus
import com.grepp.quizy.matching.domain.user.UserId
import com.grepp.quizy.matching.domain.user.UserVector
import com.grepp.quizy.matching.infra.redis.util.toByteArray
import com.grepp.quizy.matching.infra.redis.util.toFloatArray
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPooled
import redis.clients.jedis.search.IndexDefinition
import redis.clients.jedis.search.IndexOptions
import redis.clients.jedis.search.Query
import redis.clients.jedis.search.Schema

private const val MATCHING_INDEX_PREFIX = "MATCHING_VECTOR_POOL:"
private const val MATCHING_INDEX = "MATCHING_INDEX"
private const val MATCHING_K = 5

private const val ID_FIELD = "id"
private const val VECTOR_FIELD = "vector"

@Repository
class MatchingPoolRepositoryAdapter(
    private val jedis: JedisPooled
) : MatchingPoolRepository {
    private val log = KotlinLogging.logger {}

    @PostConstruct
    fun initialize() {
        log.info { "Initializing RediSearchRepository" }
        createIndex()
    }

    private fun createIndex() {
        val definition = IndexDefinition().setPrefixes(MATCHING_INDEX_PREFIX)
        val vectorAttr = mapOf(
            Pair("TYPE", "FLOAT32"),
            Pair("DIM", 15),
            Pair("DISTANCE_METRIC", "COSINE"),
        )
        val schema = Schema()
            .addNumericField(ID_FIELD)
            .addHNSWVectorField(VECTOR_FIELD, vectorAttr)

        try {
            jedis.ftCreate(MATCHING_INDEX, IndexOptions.defaultOptions().setDefinition(definition), schema)
        } catch (e: Exception) {
            log.error { "Could not create index: ${e.message}" }
        }
    }

    override fun saveVector(userStatus: UserStatus) {
        val key = "$MATCHING_INDEX_PREFIX${userStatus.userId.value}"
        jedis.hset(key, ID_FIELD, userStatus.userId.value.toString())
        jedis.hset(key.toByteArray(), VECTOR_FIELD.toByteArray(), userStatus.vector.value.toByteArray())
    }

    override fun findNearestUser(pivot: UserStatus): List<UserStatus> {
        val query = Query("*=>[KNN ${'$'}K @$VECTOR_FIELD ${'$'}query_vector]")
            .returnFields(ID_FIELD)
            .addParam("K", MATCHING_K)
            .addParam("query_vector", pivot.vector.value.toByteArray())
            .dialect(2)
        val docs = jedis.ftSearch(MATCHING_INDEX, query).documents

        return docs.map { doc ->
            val id = doc.get(ID_FIELD) as String
            val vector = jedis.hget(doc.id.toByteArray(), VECTOR_FIELD.toByteArray())
            UserStatus(
                userId = UserId(id.toLong()),
                vector = UserVector(vector.toFloatArray())
            )
        }
    }

    override fun remove(userId: UserId) {
        jedis.del("$MATCHING_INDEX_PREFIX${userId.value}")
    }
}