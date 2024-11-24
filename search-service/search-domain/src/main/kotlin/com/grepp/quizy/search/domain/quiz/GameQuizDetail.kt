package com.grepp.quizy.search.domain.quiz

data class GameQuizDetail(
    val id: Long,
    val content: String,
    val options: List<GameQuizOption>,
    val answer: GameQuizAnswer
) : AnswerableQuizDetail {
    companion object {
        fun from(quiz: AnswerableQuiz) =
            GameQuizDetail(
                id = quiz.id.value,
                content = quiz.content(),
                options = quiz.options.map { GameQuizOption(it.optionNumber, it.content) },
                answer = GameQuizAnswer(quiz.answer(), quiz.explanation())
            )
    }
}

data class GameQuizOption(val optionNumber: Int, val content: String)

data class GameQuizAnswer(val content: String, val explanation: String)