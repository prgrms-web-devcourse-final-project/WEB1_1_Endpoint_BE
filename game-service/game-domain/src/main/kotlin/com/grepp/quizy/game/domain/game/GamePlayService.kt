package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.LeaderboardInfo
import com.grepp.quizy.game.domain.message.MessagePublisher
import com.grepp.quizy.game.domain.message.StreamMessage
import com.grepp.quizy.game.domain.quiz.GameQuiz
import com.grepp.quizy.game.domain.quiz.QuizAppender
import com.grepp.quizy.game.domain.quiz.QuizFetcher
import com.grepp.quizy.game.domain.quiz.QuizReader
import com.grepp.quizy.game.domain.useranswer.UserAnswer
import com.grepp.quizy.game.domain.useranswer.UserAnswerAppender
import com.grepp.quizy.game.domain.useranswer.UserAnswerReader
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.math.roundToInt


@Service
class GamePlayService(
    private val gameReader: GameReader,
    private val quizAppender: QuizAppender,
    private val quizFetcher: QuizFetcher,
    private val gameQuizAppender: GameQuizAppender,
    private val quizReader: QuizReader,
    private val userAnswerAppender: UserAnswerAppender,
    private val userAnswerReader: UserAnswerReader,
    private val gameLeaderboardManager: GameLeaderboardManager,
    private val gameMessagePublisher: GameMessagePublisher,
    private val messageSender: GameMessageSender,
    private val ratingCalculator: RatingCalculator,
    private val taskScheduler: TaskScheduler,
    private val messagePublisher: MessagePublisher
) {

    fun startGame(gameId: Long) {
        val game = gameReader.read(gameId)
        val fetchQuizzes =
            quizFetcher.fetchQuiz(game.setting.subject, game.setting.quizCount, game.setting.level)

        val quizzes = quizAppender.appendAll(fetchQuizzes.quizzes)
        quizzes.map { quiz ->
            gameQuizAppender.appendQuiz(game.id, quiz.id)
        }
        gameLeaderboardManager.initializeLeaderboard(
            game.id,
            game.players.players.map { it.user.id }
        )
        taskScheduler.schedule(
            { endGame(game.id) },
            Instant.now().plusSeconds(game.setting.quizCount * 10L + 1L)
        )
        gameMessagePublisher.publish(
            GameMessage.quiz(
                game.id,
                quizzes
            )
        )
    }

    fun submitChoice(
        gameId: Long,
        userId: Long,
        quizId: Long,
        choice: String,
        submissionTimestamp: Long
    ) {
        val quiz =
            quizReader.read(quizId)
        val (isCorrect, score: Int) =
            gradeChoice(quiz, choice, submissionTimestamp)

        sendResultToUser(userId, gameId, quiz, score, isCorrect)
        userAnswerAppender.append(
            UserAnswer.from(userId, gameId, quiz.content, choice, quiz.answer, isCorrect)
        )

        reflectScoreToLeaderboard(gameId, userId, score)
        publishLeaderboard(gameId)
    }

    private fun gradeChoice(
        quiz: GameQuiz,
        choice: String,
        submissionTimestamp: Long
    ): Pair<Boolean, Int> {
        val isCorrect = quiz.answer.content == choice
        val timeTakenMillis = (System.currentTimeMillis() - submissionTimestamp)

        val score = if (isCorrect) {
            val baseScore = 100.0
            val deductionPerMillis = 0.01  // 1초당 10점 감점
            val calculatedScore = baseScore - (timeTakenMillis * deductionPerMillis)

            val finalScore = maxOf(calculatedScore, 50.0)  // 최소 50점

            finalScore.roundToInt()
        } else {
            0
        }
        return Pair(isCorrect, score)
    }

    private fun sendResultToUser(
        userId: Long,
        gameId: Long,
        quiz: GameQuiz,
        score: Int,
        isCorrect: Boolean
    ) {
        messageSender.send(
            userId = userId.toString(),
            message = GameMessage.quizAnswer(
                gameId,
                quiz,
                score,
                isCorrect
            )
        )
    }

    private fun reflectScoreToLeaderboard(gameId: Long, userId: Long, score: Int) {
        gameLeaderboardManager.incrementScore(gameId, userId, score)
    }

    private fun publishLeaderboard(gameId: Long) {
        val candidateLeaderboard = gameLeaderboardManager.getLeaderboard(gameId)
        val leaderboardInfos = candidateLeaderboard.map {
            LeaderboardInfo.of(it.key, it.value)
        }
        gameMessagePublisher.publish(
            GameMessage.leaderboard(
                gameId,
                leaderboardInfos
            )
        )
    }

    fun endGame(gameId: Long) {
        val game = gameReader.read(gameId)

        val leaderboard = gameLeaderboardManager.getLeaderboard(gameId)
        val ratings = game.players.players.associate { it.user.id to it.user.rating }

        val newRatings = if (game.type == GameType.RANDOM) {
            ratingCalculator.calculateElo(leaderboard, ratings)
        } else {
            ratings
        }

        game.players.players.map {
            val ratingDiff = newRatings[it.user.id]!! - ratings[it.user.id]!!

            messageSender.send(
                it.user.id.toString(),
                GameMessage.result(
                    gameId,
                    gameLeaderboardManager.getRank(gameId, it.user.id),
                    newRatings[it.user.id]!!,
                    ratingDiff,
                    userAnswerReader.readAll(
                        it.user.id, gameId
                    )
                )
            )
            messagePublisher.publish(
                StreamMessage.rating(
                    mapOf(
                        "userId" to it.user.id.toString(),
                        "rating" to newRatings[it.user.id].toString()
                    )
                )
            )
        }
        messagePublisher.publish(
            StreamMessage.gameDestroy(
                mapOf(
                    "gameId" to gameId.toString()
                )
            )
        )
    }

}