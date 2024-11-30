package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.QuizCounter
import com.grepp.quizy.quiz.domain.quiz.QuizReader
import org.springframework.stereotype.Service

@Service
class UserAnswerService(
    private val quizReader: QuizReader,
    private val quizCounter: QuizCounter,
    private val userAnswerAppender: UserAnswerAppender,
) : UserAnswerCreateUseCase {

    override fun createUserAnswer(
            id: UserAnswerId,
            userChoice: Int,
    ): UserAnswer {
        val quiz = quizReader.read(id.quizId)
        quiz.validateChoice(userChoice)
        quizCounter.increaseSelectionCount(id.quizId, userChoice)
        return userAnswerAppender.append(quiz, id, userChoice)
    }
}
