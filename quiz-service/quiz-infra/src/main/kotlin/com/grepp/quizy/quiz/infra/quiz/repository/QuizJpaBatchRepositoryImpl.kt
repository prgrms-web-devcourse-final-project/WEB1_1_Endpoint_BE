package com.grepp.quizy.quiz.infra.quiz.repository

import com.grepp.quizy.quiz.infra.quiz.entity.QuizEntity
import jakarta.persistence.EntityManager
import org.springframework.transaction.annotation.Transactional

@Transactional
class QuizJpaBatchRepositoryImpl(
        private val entityManager: EntityManager
) : QuizJpaBatchRepository {

    override fun likeCountBatchUpdate(updates: Map<Long, Long>) {
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

    override fun selectionCountBatchUpdate(updates: Map<Long, Map<Int, Long>>) {
        if (updates.isEmpty()) return

        val quizzes = entityManager.createQuery(
            """
        SELECT q FROM QuizEntity q 
        WHERE q.id IN :quizIds
        """, QuizEntity::class.java
        )
            .setParameter("quizIds", updates.keys)
            .resultList

        quizzes.forEach { quiz ->
            updates[quiz.quizId]?.forEach { (optionNumber, count) ->
                quiz.updateOptionSelectionCount(optionNumber, count)
            }
        }

        entityManager.flush()
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
