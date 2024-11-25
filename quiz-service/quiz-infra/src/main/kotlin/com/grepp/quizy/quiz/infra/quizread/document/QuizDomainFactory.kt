package com.grepp.quizy.quiz.infra.quizread.document

import com.grepp.quizy.quiz.domain.quizread.*

class QuizDomainFactory {
    companion object {
        fun toQuiz(document: QuizDocument): QuizForRead {
            return when (document.type) {
                QuizType.AB -> ABTest(document)
                QuizType.OX -> OXQuiz(document)
                QuizType.MULTIPLE_CHOICE ->
                        MultipleOptionQuiz(document)
            }
        }

        fun toAnswerableQuiz(document: QuizDocument): AnswerableQuiz {
            return when (document.type) {
                QuizType.OX -> OXQuiz(document)
                QuizType.MULTIPLE_CHOICE -> MultipleOptionQuiz(document)
                QuizType.AB -> throw IllegalArgumentException("Unexpected QuizType ${document.type}")
            }
        }

        private fun ABTest(document: QuizDocument): ABTest =
                with(document) {
                    return ABTest(
                            id = QuizId(id),
                            category = category,
                            content = QuizContent(type, content),
                            tags = tags.map { QuizTag(it) },
                            options =
                                    options.map { option ->
                                        QuizOption(
                                                option.optionNumber,
                                                option.content,
                                                selectionPerOption[
                                                        option
                                                                .optionNumber]
                                                        ?: 0,
                                        )
                                    },
                    )
                }

        private fun OXQuiz(document: QuizDocument): OXQuiz =
                with(document) {
                    return OXQuiz(
                            id = QuizId(id),
                            category = category,
                            content = QuizContent(type, content),
                            tags = tags.map { QuizTag(it) },
                            options =
                                    options.map { option ->
                                        QuizOption(
                                                option.optionNumber,
                                                option.content,
                                                selectionPerOption[
                                                        option
                                                                .optionNumber]
                                                        ?: 0,
                                        )
                                    },
                            answer =
                                    QuizAnswer(
                                            answer!!.value,
                                            answer.explanation,
                                    ),
                    )
                }

        private fun MultipleOptionQuiz(
                document: QuizDocument
        ): MultipleOptionQuiz =
                with(document) {
                    return MultipleOptionQuiz(
                            id = QuizId(id),
                            category = category,
                            content = QuizContent(type, content),
                            tags = tags.map { QuizTag(it) },
                            options =
                                    options.map { option ->
                                        QuizOption(
                                                option.optionNumber,
                                                option.content,
                                                selectionPerOption[
                                                        option
                                                                .optionNumber]
                                                        ?: 0,
                                        )
                                    },
                            answer =
                                    QuizAnswer(
                                            answer!!.value,
                                            answer.explanation,
                                    ),
                    )
                }
    }
}
