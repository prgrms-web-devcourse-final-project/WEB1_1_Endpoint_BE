package com.grepp.quizy.quiz.domain

class MultipleChoiceQuiz(
        content: QuizContent,
        tags: List<QuizTag>,
        options: List<QuizOption>,
        private val answer: QuizAnswer,
        id: QuizId = QuizId(0),
) : Quiz(content, tags, options, id), Answerable {

    init {
        validateOptions(4)
        require(
                answer.value in
                        options.indices.map {
                            it.toString()
                        }
        ) {
            "Multiple choice quiz answer must be between 0 and ${options.size - 1}"
        }
    }

    override fun getAnswer(): QuizAnswer = answer

    override fun getCorrectAnswer(): String = answer.value

    override fun getAnswerExplanation(): String =
            answer.explanation
}
