package com.grepp.quizy.user.domain.quiz

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class QuizScoreReader(
    private val quizClient: QuizClient
) {
    private val logger = LoggerFactory.getLogger(QuizScoreReader::class.java)

    suspend fun getQuizScore(userId: Long): UserQuizScore? {
        return try {
            withContext(Dispatchers.IO) {
                quizClient.getUserQuizScore(userId)
            }
        } catch (ex: Exception) {
            logger.error("Failed to fetch scores for user $userId", ex)
            null
        }
    }
}