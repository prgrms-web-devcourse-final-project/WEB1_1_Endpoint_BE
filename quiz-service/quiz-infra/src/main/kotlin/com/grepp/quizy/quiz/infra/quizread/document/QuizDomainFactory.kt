package com.grepp.quizy.quiz.infra.quizread.document

import com.grepp.quizy.common.dto.DateTime
import com.grepp.quizy.quiz.domain.image.QuizImageId
import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.domain.user.UserId

class QuizDomainFactory {
    companion object {
        fun toQuiz(document: QuizDocument): Quiz {
            return when (document.type) {
                QuizType.AB_TEST -> ABTest(document)
                QuizType.OX -> OXQuiz(document)
                QuizType.MULTIPLE_CHOICE ->
                        MultipleChoiceQuiz(document)
            }
        }

        fun toAnswerableQuiz(document: QuizDocument): Quiz {
            return when (document.type) {
                QuizType.OX -> OXQuiz(document)
                QuizType.MULTIPLE_CHOICE -> MultipleChoiceQuiz(document)
                QuizType.AB_TEST -> throw IllegalArgumentException("Unexpected QuizType ${document.type}")
            }
        }

        private fun ABTest(document: QuizDocument): ABTest =
                with(document) {
                    return ABTest.of(
                        userId = UserId(userId),
                        content = QuizContent(
                            category,
                            content,
                            tags.map { QuizTag(it) },
                            options.map { option ->
                                QuizOption.ABTestOption(
                                    option.optionNumber,
                                    option.content,
                                    option.imageId?.let { QuizImageId.from(it) },
                                    option.selectionCount
                                )
                            }),
                        id = QuizId(id),
                        dateTime = DateTime(createdAt, updatedAt),
                        commentCount = 0
                    )
                }

        private fun OXQuiz(document: QuizDocument): OXQuiz =
                with(document) {
                    return OXQuiz.of(
                        userId = UserId(userId),
                        content = QuizContent(
                            category,
                            content,
                            tags.map { QuizTag(it) },
                            options = options.map { option ->
                                    QuizOption.OXOption(
                                        option.optionNumber,
                                        option.content,
                                        option.selectionCount
                                    )
                                    },
                        ),
                        id = QuizId(id),
                        dateTime = DateTime(createdAt, updatedAt),
                        answer = QuizAnswer(answer!!.answerNumber, answer!!.explanation),
                        commentCount = 0
                    )
                }

        private fun MultipleChoiceQuiz(
                document: QuizDocument
        ): MultipleChoiceQuiz =
                with(document) {
                    return MultipleChoiceQuiz.of(
                        userId = UserId(userId),
                        content = QuizContent(
                            category,
                            content,
                            tags.map { QuizTag(it) },
                            options = options.map { option ->
                                QuizOption.MultipleChoiceOption(
                                    option.optionNumber,
                                    option.content,
                                    option.selectionCount
                                )
                            },
                        ),
                        id = QuizId(id),
                        dateTime = DateTime(createdAt, updatedAt),
                        answer = QuizAnswer(answer!!.answerNumber, answer!!.explanation),
                        commentCount = 0
                    )
                }
    }
}
