package com.grepp.quizy.quiz.domain

class OXQuiz(
        content: QuizContent,
        tags: List<QuizTag>,
        options: List<QuizOption>,
        private val answer: QuizAnswer,
        id: QuizId = QuizId(0),
) : Quiz(content, tags, options, id), Answerable {

    init {
        validateOptions(2)
        require(answer.value in setOf("O", "X")) {
            "OX 퀴즈의 답은 O 또는 X 여야 합니다"
        }
    }

    override fun getAnswer(): QuizAnswer = answer

    override fun getCorrectAnswer(): String = answer.value

    override fun getAnswerExplanation(): String =
            answer.explanation
}
