package com.grepp.quizy.quiz.domain

import org.springframework.stereotype.Component

@Component
class QuizAppender(
        private val quizRepository: QuizRepository
) {
    fun append(
            content: QuizContent,
            tags: List<QuizTag>,
            options: List<QuizOption>,
            answer: QuizAnswer,
    ): Quiz {
        val quiz =
                when (content.type) {
                    QuizType.OX ->
                            OXQuiz(
                                    content,
                                    tags,
                                    options,
                                    answer,
                            )
                    QuizType.AB_TEST ->
                            ABTest(content, tags, options)
                    QuizType.MULTIPLE_CHOICE ->
                            MultipleChoiceQuiz(
                                    content,
                                    tags,
                                    options,
                                    answer,
                            )
                }
        return quizRepository.save(quiz)
    }
}
