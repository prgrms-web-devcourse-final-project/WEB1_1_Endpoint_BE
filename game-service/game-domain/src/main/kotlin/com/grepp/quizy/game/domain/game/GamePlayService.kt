package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.LeaderboardInfo
import com.grepp.quizy.game.domain.message.MessagePublisher
import com.grepp.quizy.game.domain.message.StreamMessage
import com.grepp.quizy.game.domain.quiz.GameQuiz
import com.grepp.quizy.game.domain.quiz.QuizAppender
import com.grepp.quizy.game.domain.quiz.QuizFetcher
import com.grepp.quizy.game.domain.quiz.QuizReader
import com.grepp.quizy.game.domain.user.UserUpdater
import com.grepp.quizy.game.domain.useranswer.UserAnswer
import com.grepp.quizy.game.domain.useranswer.UserAnswerAppender
import com.grepp.quizy.game.domain.useranswer.UserAnswerReader
import org.springframework.context.event.EventListener
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
    private val gameQuizReader: GameQuizReader,
    private val quizReader: QuizReader,
    private val userAnswerAppender: UserAnswerAppender,
    private val userAnswerReader: UserAnswerReader,
    private val userUpdater: UserUpdater,
    private val gameLeaderboardManager: GameLeaderboardManager,
    private val gameMessagePublisher: GameMessagePublisher,
    private val messageSender: GameMessageSender,
    private val gameManager: GameManager,
    private val ratingCalculator: RatingCalculator,
    private val taskScheduler: TaskScheduler,
    private val messagePublisher: MessagePublisher
) {

    // 게임 로딩..? 비동기 추가(코루틴?)
    @EventListener
    fun handleGameStartedEvent(event: GameStartEvent) {
        val fetchQuizzes =
            quizFetcher.fetchQuiz(event.game.setting.subject, event.game.setting.quizCount, event.game.setting.level)

        val quizzes = quizAppender.appendAll(fetchQuizzes.quizzes)
        quizzes.map { quiz ->
            gameQuizAppender.appendQuiz(event.game.id, quiz.id)
        }
        // 랭킹 리더보드 초기화
        gameLeaderboardManager.initializeLeaderboard(
            event.game.id,
            event.game.players.players.map { it.user.id }
        )
        taskScheduler.schedule(
            { endGame(event.game.id) },
            Instant.now().plusSeconds(event.game.setting.quizCount * 10L + 1L)
        )
        // 게임에서 사용할 퀴즈 전송
        gameMessagePublisher.publish(
            GameMessage.quiz(
                event.game.id,
                quizzes
            )
        )
//        publishQuiz(event.game.id)
    }

    fun publishQuiz(gameId: Long) {
        val game = gameReader.read(gameId)
        val quizIds = gameQuizReader.read(gameId)
        val quizzes = quizIds.map { quizId ->
            quizReader.read(quizId)
        }
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
            messagePublisher.publish(
                StreamMessage.gameDestroy(
                    mapOf(
                        "gameId" to gameId.toString()
                    )
                )
            )
        }
        // 게임 정보 삭제
        gameManager.destroy(game)
    }

}