package com.grepp.quizy.quiz.infra.quiz.repository

import jakarta.persistence.EntityManager
import org.springframework.transaction.annotation.Transactional

@Transactional
class QuizJpaBatchRepositoryImpl(
        private val entityManager: EntityManager
) : QuizJpaBatchRepository {

    override fun batchUpdate(updates: Map<Long, Long>) {
        if (updates.isEmpty()) return

        val batchSize = 1000
        updates.entries.chunked(batchSize).forEach { batch ->
            val queryString = buildBatchUpdateQuery(batch)

            val query = entityManager.createQuery(queryString)

            batch.forEach { (quizId, count) ->
                query.setParameter("id_${quizId}", quizId)
                query.setParameter("count_${quizId}", count)
            }
            query.setParameter("ids", batch.map { it.key })

            query.executeUpdate()
        }

        entityManager.flush()
        entityManager.clear()
    }

    private fun buildBatchUpdateQuery(
            batch: List<Map.Entry<Long, Long>>
    ): String {
        return """
        UPDATE QuizEntity q 
        SET q.likeCount = q.likeCount + CASE q.id 
            ${batch.joinToString("\n") {
            "WHEN :id_${it.key} THEN :count_${it.key}"
        }}
            ELSE 0 
        END,
        q.updatedAt = CURRENT_TIMESTAMP
        WHERE q.id IN :ids
    """
                .trimIndent()
    }
}
