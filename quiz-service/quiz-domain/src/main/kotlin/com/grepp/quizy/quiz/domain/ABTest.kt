package com.grepp.quizy.quiz.domain

class ABTest(
        content: QuizContent,
        tags: List<QuizTag>,
        options: List<QuizOption>,
        id: QuizId = QuizId(0),
) : Quiz(content, tags, options, id) {

    init {
        validateOptions(2)
    }
}
