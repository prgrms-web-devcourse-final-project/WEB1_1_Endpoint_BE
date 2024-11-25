package com.grepp.quizy.quiz.domain.quizread

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
    val answer: QuizAnswer,
) : QuizForRead(id, content, category, tags, options), Answerable

class ABTest(
    id: QuizId,
    content: QuizContent,
    category: QuizCategory,
    tags: List<QuizTag>,
    options: List<QuizOption>,
) : QuizForRead(id, content, category, tags, options)

class OXQuiz(
    id: QuizId,
    content: QuizContent,
    category: QuizCategory,
    tags: List<QuizTag>,
    options: List<QuizOption>,
    answer: QuizAnswer,
) : AnswerableQuiz(id, content, category, tags, options, answer) {
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
) : AnswerableQuiz(id, content, category, tags, options, answer) {
    override fun answer() = answer.value

    override fun explanation() = answer.explanation
}
