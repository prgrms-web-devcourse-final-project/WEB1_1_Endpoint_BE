package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.*
import com.grepp.quizy.game.domain.quiz.GameQuiz
import com.grepp.quizy.game.domain.quiz.QuizAppender
import com.grepp.quizy.game.domain.quiz.QuizFetcher
import com.grepp.quizy.game.domain.quiz.QuizReader
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class GamePlayService(
    private val gameReader: GameReader,
    private val quizAppender: QuizAppender,
    private val quizFetcher: QuizFetcher,
    private val gameQuizAppender: GameQuizAppender,
    private val gameQuizReader: GameQuizReader,
    private val quizReader: QuizReader,
    private val gameLeaderboardManager: GameLeaderboardManager,
    private val messagePublisher: GameMessagePublisher,
    private val messageSender: GameMessageSender
) {

    // 게임 로딩..? 비동기 추가(코루틴?)
    @EventListener
    fun handleGameStartedEvent(event: GameStartEvent) {
        val fetchQuizzes =
            quizFetcher.fetchQuiz(event.game.setting.subject, event.game.setting.quizCount, event.game.setting.level)

        val quizzes = quizAppender.appendAll(fetchQuizzes)
        quizzes.map { quiz ->
            gameQuizAppender.appendQuiz(event.game.id, quiz.id)
        }
        // 랭킹 리더보드 초기화
        gameLeaderboardManager.initializeLeaderboard(
            event.game.id,
            event.game.players.players.map { it.user.id }
        )
        // 게임에서 사용할 퀴즈 전송
        publishQuiz(event.game.id)
    }

    fun publishQuiz(gameId: Long) {
        val game = gameReader.read(gameId)
        val quizIds = gameQuizReader.read(gameId)
        val quizzes = quizIds.map { quizId ->
            quizReader.read(quizId)
        }
        messagePublisher.publish(
            GameMessage.quiz(
                game.id,
                quizzes
            )
        )
    }

    fun submitAnswer(
        gameId: Long,
        userId: Long,
        quizId: Long,
        answer: String,
        submissionTimestamp: Long
    ) {
        val quiz =
            quizReader.read(quizId)
        val (isCorrect, score: Double) =
            gradeAnswer(quiz, answer, submissionTimestamp)

        sendResultToUser(userId, gameId, quiz, score, isCorrect)

        reflectScoreToLeaderboard(gameId, userId, score)
        publishLeaderboard(gameId)
    }

    private fun gradeAnswer(
        quiz: GameQuiz,
        answer: String,
        submissionTimestamp: Long
    ): Pair<Boolean, Double> {
        val isCorrect = quiz.answer.content == answer
        val timeTakenMillis = (System.currentTimeMillis() - submissionTimestamp).toDouble()

        /*
        * 기본 점수 10점
        * 1밀리초당 0.0005점 감점 (1초당 0.5점)
        * 아주 늦게라도 맞춘다면 5점
        * */

        val score = if (isCorrect) {
            val baseScore = 10.0
            val deductionPerMillis = 0.0005
            val calculatedScore = baseScore - (timeTakenMillis * deductionPerMillis)

            val finalScore = maxOf(calculatedScore, 5.0)

            (finalScore * 1000).roundToInt().toDouble() / 1000
        } else {
            0.0
        }
        return Pair(isCorrect, score)
    }

    private fun sendResultToUser(
        userId: Long,
        gameId: Long,
        quiz: GameQuiz,
        score: Double,
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

    private fun reflectScoreToLeaderboard(gameId: Long, userId: Long, score: Double) {
        gameLeaderboardManager.incrementScore(gameId, userId, score)
    }

    private fun publishLeaderboard(gameId: Long) {
        val candidateLeaderboard = gameLeaderboardManager.getLeaderboard(gameId)
        val leaderboardInfos = candidateLeaderboard.map {
            LeaderboardInfo.of(it.key, it.value)
        }
        messagePublisher.publish(
            GameMessage.leaderboard(
                gameId,
                leaderboardInfos
            )
        )
    }

    // TODO: 게임 종료
    fun endGame() {
        // 개인에게 결과 전송..?
        // 레디스에 있는 게임 정보 Mysql에 저장
        // 레디스에 있는 게임 정보 삭제
    }

}