package com.grepp.quizy.user.domain.game

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RatingReader(
    private val gameClient: GameClient,
) {
    private val logger = LoggerFactory.getLogger(RatingReader::class.java)

    suspend fun getRating(userId: Long): UserRating? {
        return try {
            withContext(Dispatchers.IO) {
                gameClient.getUserRating(userId)
            }
        } catch (ex: Exception) {
            logger.error("Failed to fetch scores for user $userId", ex)
            null
        }
    }
}