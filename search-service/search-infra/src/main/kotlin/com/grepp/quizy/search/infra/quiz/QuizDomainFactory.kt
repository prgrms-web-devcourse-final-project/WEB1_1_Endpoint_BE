package com.grepp.quizy.search.infra.quiz

import com.grepp.quizy.search.domain.quiz.*

class QuizDomainFactory {
    companion object {
        fun toQuiz(document: QuizDocument): Quiz {
            return when (document.type) {
                QuizType.AB -> ABTest(document)
                QuizType.OX -> OXQuiz(document)
                QuizType.MULTIPLE_CHOICE -> MultipleOptionQuiz(document)
            }
        }

        private fun ABTest(document: QuizDocument): ABTest = with(document) {
            return ABTest(
                id = QuizId(id),
                category = QuizCategory(category),
                content = QuizContent(type, content),
                tags = tags.split(" ").map { QuizTag(it) },
                options = options.map { QuizOption(it.optionNumber, it.content, 0) }
            )
        }

        private fun OXQuiz(document: QuizDocument): OXQuiz = with(document) {
            return OXQuiz(
                id = QuizId(id),
                category = QuizCategory(category),
                content = QuizContent(type, content),
                tags = tags.split(" ").map { QuizTag(it) },
                options = options.map { QuizOption(it.optionNumber, it.content, 0) },
                answer = QuizAnswer(answer!!.value, answer.explanation)
            )
        }

        private fun MultipleOptionQuiz(document: QuizDocument): MultipleOptionQuiz = with(document) {
            return MultipleOptionQuiz(
                id = QuizId(id),
                category = QuizCategory(category),
                content = QuizContent(type, content),
                tags = tags.split(" ").map { QuizTag(it) },
                options = options.map { QuizOption(it.optionNumber, it.content, 0) },
                answer = QuizAnswer(answer!!.value, answer.explanation)
            )
        }
    }
}