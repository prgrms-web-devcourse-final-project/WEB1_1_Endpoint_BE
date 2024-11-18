package com.grepp.quizy.quiz.domain.quiz

class MultipleChoiceQuiz
private constructor(
        content: QuizContent,
        private var _answer: QuizAnswer,
        type: QuizType = QuizType.MULTIPLE_CHOICE,
        id: QuizId = QuizId(0),
) : Quiz(type, content, id), Answerable {

    val answer: QuizAnswer
        get() = _answer

    init {
        validateOptions(4)
        validateAnswer()
    }

    companion object {
        fun create(
                content: QuizContent,
                answer: QuizAnswer,
        ): MultipleChoiceQuiz {
            return MultipleChoiceQuiz(content, answer)
        }

        fun of(
                content: QuizContent,
                answer: QuizAnswer,
                id: QuizId,
        ): MultipleChoiceQuiz {
            return MultipleChoiceQuiz(
                    content,
                    answer,
                    id = id,
            )
        }
    }

    override fun validateAnswer() {
        require(
                answer.value in
                        content.options.indices.map {
                            it.toString()
                        }
        ) {
            "객관식 퀴즈의 답은 0부터 ${content.options.size - 1} 사이의 값이어야 합니다"
        }
    }

    override fun getQuizAnswer(): QuizAnswer {
        return answer
    }

    override fun updateAnswer(
            newAnswer: QuizAnswer
    ): Answerable {
        _answer = newAnswer
        return this
    }
}
