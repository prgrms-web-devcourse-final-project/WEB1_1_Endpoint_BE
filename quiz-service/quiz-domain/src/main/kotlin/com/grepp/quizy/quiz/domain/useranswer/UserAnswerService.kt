package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.common.dto.Cursor
import com.grepp.quizy.common.dto.SliceResult
import com.grepp.quizy.quiz.domain.quiz.QuizCounter
import com.grepp.quizy.quiz.domain.quiz.QuizReader
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class UserAnswerService(
    private val quizReader: QuizReader,
    private val quizCounter: QuizCounter,
    private val userAnswerAppender: UserAnswerAppender,
    private val userAnswerReader: UserAnswerReader,
    private val userAnswerCombiner: UserAnswerCombiner,
    private val userAnswerUpdater: UserAnswerUpdater,
    private val userAnswerValidator: UserAnswerValidator,
) : UserAnswerCreateUseCase, UserAnswerReadService, UserAnswerUpdateUseCase {

    override fun createUserAnswer(
        key: UserAnswerKey,
        userChoice: Int,
    ): UserAnswer {
        val quiz = quizReader.read(key.quizId)
        userAnswerValidator.validate(key)
        quizCounter.increaseSelectionCount(key.quizId, userChoice)
        return userAnswerAppender.append(quiz, key, userChoice)
    }

    override fun getIncorrectQuizzes(userId: UserId, cursor: Cursor): SliceResult<QuizWithUserAnswer> {
        val incorrectAnswers = userAnswerReader.readIncorrect(userId, cursor)
        return userAnswerCombiner.combineWithQuiz(incorrectAnswers)
    }

    override fun reviewUserAnswer(key: UserAnswerKey, reviewStatus: ReviewStatus) {
        val userAnswer = userAnswerReader.read(key)
        userAnswer.review(reviewStatus)
        userAnswerUpdater.update(userAnswer)
    }
}
