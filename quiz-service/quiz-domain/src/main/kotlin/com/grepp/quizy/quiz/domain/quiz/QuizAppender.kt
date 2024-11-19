package com.grepp.quizy.quiz.domain.quiz

import org.springframework.stereotype.Component

@Component
class QuizAppender(
        private val quizRepository: QuizRepository
) {
    fun append(
            type: QuizType,
            content: QuizContent,
            answer: QuizAnswer,
    ): Quiz {
        val quiz =
                when (type) {
                    QuizType.OX ->
                            OXQuiz.create(content, answer)
                    QuizType.AB_TEST ->
                            ABTest.create(content)
                    QuizType.MULTIPLE_CHOICE ->
                            MultipleChoiceQuiz.create(
                                    content,
                                    answer,
                            )
                }
        return quizRepository.save(quiz)
    }
}
