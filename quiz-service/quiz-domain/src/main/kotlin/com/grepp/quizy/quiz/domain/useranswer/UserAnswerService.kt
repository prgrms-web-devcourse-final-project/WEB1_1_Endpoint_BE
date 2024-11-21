package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.quiz.domain.quiz.QuizReader
import org.springframework.stereotype.Service

@Service
class UserAnswerService(
        private val quizReader: QuizReader,
        private val userAnswerAppender: UserAnswerAppender,
) : UserAnswerCreateUseCase {

    override fun create(
            id: UserAnswerId,
            userChoice: String,
    ): UserAnswer {
        val quiz = quizReader.read(id.quizId)
        return userAnswerAppender.append(quiz, id, userChoice)
    }
}
