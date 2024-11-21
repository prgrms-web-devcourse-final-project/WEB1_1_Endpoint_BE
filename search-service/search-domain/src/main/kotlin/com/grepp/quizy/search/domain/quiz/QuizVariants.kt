package com.grepp.quizy.search.domain.quiz

interface Answerable {
    fun answer(): String

    fun explanation(): String
}

class ABTest(
        id: QuizId,
        content: QuizContent,
        category: QuizCategory,
        tags: List<QuizTag>,
        options: List<QuizOption>,
        count: QuizCount,
) : Quiz(id, content, category, tags, options, count)

class OXQuiz(
        id: QuizId,
        content: QuizContent,
        category: QuizCategory,
        tags: List<QuizTag>,
        options: List<QuizOption>,
        private val answer: QuizAnswer,
        count: QuizCount,
) : Quiz(id, content, category, tags, options, count), Answerable {
    override fun answer() = answer.value

    override fun explanation() = answer.explanation
}

class MultipleOptionQuiz(
        id: QuizId,
        content: QuizContent,
        category: QuizCategory,
        tags: List<QuizTag>,
        options: List<QuizOption>,
        private val answer: QuizAnswer,
        count: QuizCount,
) : Quiz(id, content, category, tags, options, count), Answerable {
    override fun answer() = answer.value

    override fun explanation() = answer.explanation
}
