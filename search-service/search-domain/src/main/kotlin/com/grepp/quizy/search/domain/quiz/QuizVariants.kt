package com.grepp.quizy.search.domain.quiz

interface Answerable {
    fun answer(): String

    fun explanation(): String
}

abstract class AnswerableQuiz(
    id: QuizId,
    content: QuizContent,
    category: QuizCategory,
    tags: List<QuizTag>,
    options: List<QuizOption>,
    count: QuizCount,
    val answer: QuizAnswer,
) : Quiz(id, content, category, tags, options, count), Answerable

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
        answer: QuizAnswer,
        count: QuizCount,
) : AnswerableQuiz(id, content, category, tags, options, count, answer) {
    override fun answer() = answer.value

    override fun explanation() = answer.explanation
}

class MultipleOptionQuiz(
        id: QuizId,
        content: QuizContent,
        category: QuizCategory,
        tags: List<QuizTag>,
        options: List<QuizOption>,
        answer: QuizAnswer,
        count: QuizCount,
) : AnswerableQuiz(id, content, category, tags, options, count, answer) {
    override fun answer() = answer.value

    override fun explanation() = answer.explanation
}
