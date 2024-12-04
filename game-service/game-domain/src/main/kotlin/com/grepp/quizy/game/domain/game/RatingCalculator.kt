package com.grepp.quizy.game.domain.game

import org.springframework.stereotype.Component
import kotlin.math.pow

@Component
class RatingCalculator {
    fun calculateElo(
        scores: Map<Long, Int>,
        rating: Map<Long, Int>
    ): Map<Long, Int> {
        val K = 32.0
        val newRatings = mutableMapOf<Long, Int>()
        val sortedPlayerIds = scores.keys.sortedByDescending { scores[it] }

        for (i in sortedPlayerIds.indices) {
            val playerId = sortedPlayerIds[i]
            var ratingChange = 0.0

            for (j in sortedPlayerIds.indices) {
                if (i == j) continue

                val opponentId = sortedPlayerIds[j]

                val expectedScore =
                    1.0 / (1.0 + 10.0.pow((rating[opponentId]!! - rating[playerId]!!).toDouble() / 400.0))

                val scoreDiff = scores[playerId]!! - scores[opponentId]!!
                val actualScore = if (scoreDiff > 0) 1.0 else if (scoreDiff == 0) 0.5 else 0.0

                ratingChange += K * (actualScore - expectedScore)
            }

            ratingChange /= (rating.size - 1)

            newRatings[playerId] = (rating[playerId]!! + ratingChange).toInt()
        }

        return newRatings
    }
}