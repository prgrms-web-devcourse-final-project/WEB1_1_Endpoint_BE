package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizAppender(
        private val quizRepository: QuizRepository,
        private val quizMessageSender: QuizMessageSender,
) {
    fun append(
            creatorId: UserId,
            type: QuizType,
            content: QuizContent,
            answer: QuizAnswer,
    ): Quiz {
        val quiz =
                when (type) {
                    QuizType.OX ->
                            OXQuiz.create(creatorId, content, answer)
                    QuizType.AB_TEST ->
                            ABTest.create(creatorId, content)
                    QuizType.MULTIPLE_CHOICE ->
                            MultipleChoiceQuiz.create(
                                    creatorId,
                                    content,
                                    answer,
                            )
                }
        val savedQuiz = quizRepository.save(quiz)
        quizMessageSender.send(QuizCreatedEvent.from(savedQuiz))
        return savedQuiz
    }
}
