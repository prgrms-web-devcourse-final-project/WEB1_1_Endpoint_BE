package com.grepp.quizy.game.domain.game

import com.grepp.quizy.game.domain.GameMessage
import com.grepp.quizy.game.domain.QuizPayload
import com.grepp.quizy.game.domain.quiz.QuizAppender
import com.grepp.quizy.game.domain.quiz.QuizFetcher
import com.grepp.quizy.game.domain.quiz.QuizReader
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class GameManageService(
    private val gameReader: GameReader,
    private val quizAppender: QuizAppender,
    private val quizFetcher: QuizFetcher,
    private val gameQuizAppender: GameQuizAppender,
    private val quizGameReader: GameQuizReader,
    private val quizReader: QuizReader,
    private val messagePublisher: GameMessagePublisher,
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

        // 게임에서 사용할 퀴즈 전송
        publishQuiz(event.game.id)
    }

    fun publishQuiz(gameId: Long) {
        val game = gameReader.read(gameId)
        val quizIds = quizGameReader.read(gameId)
        val quizzes = quizIds.map { quizId ->
            quizReader.read(quizId)
        }
        messagePublisher.publish(
            GameMessage.quiz(
                game.id,
                QuizPayload.from(
                    quizzes
                )
            )
        )
    }

    // TODO: 퀴즈 채점
    fun gradeQuiz() {
        // 채점 로직 추가
        // 개인에게 결과 전송
        // 리더보드 업데이트
    }

    // TODO: 게임 종료
    fun endGame() {
        // 개인에게 결과 전송..?
        // 레디스에 있는 게임 정보 Mysql에 저장
        // 레디스에 있는 게임 정보 삭제
    }

}