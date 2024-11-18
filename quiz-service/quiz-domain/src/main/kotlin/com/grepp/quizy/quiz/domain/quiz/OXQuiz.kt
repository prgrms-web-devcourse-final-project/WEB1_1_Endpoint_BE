package com.grepp.quizy.quiz.domain.quiz

class OXQuiz
private constructor(
        content: QuizContent,
        private var _answer: QuizAnswer,
        type: QuizType = QuizType.OX,
        id: QuizId = QuizId(0),
) : Quiz(type, content, id), Answerable {

    val answer: QuizAnswer
        get() = _answer

    init {
        validateOptions(2)
        validateAnswer()
    }

    companion object {
        fun create(
                content: QuizContent,
                answer: QuizAnswer,
        ): OXQuiz {
            return OXQuiz(content, answer)
        }

        fun of(
                content: QuizContent,
                answer: QuizAnswer,
                id: QuizId,
        ): OXQuiz {
            return OXQuiz(content, answer, id = id)
        }
    }

    override fun getQuizAnswer(): QuizAnswer {
        return answer
    }

    override fun validateAnswer() {
        require(answer.value in listOf("O", "X")) {
            "OX 퀴즈의 답은 O 또는 X 여야 합니다"
        }
    }

    override fun updateAnswer(
            newAnswer: QuizAnswer
    ): Answerable {
        _answer = newAnswer
        return this
    }
}
