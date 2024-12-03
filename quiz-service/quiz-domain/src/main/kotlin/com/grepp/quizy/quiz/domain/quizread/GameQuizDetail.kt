package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.quiz.Answerable
import com.grepp.quizy.quiz.domain.quiz.Quiz
import com.grepp.quizy.quiz.domain.quiz.QuizOption

data class GameQuizDetail(
    val id: Long,
    val content: String,
    val options: List<GameQuizOption>,
    val answer: GameQuizAnswer
) : AnswerableQuizDetail {
    companion object {
        fun from(quiz: Quiz): GameQuizDetail {
            if (quiz !is Answerable) throw IllegalArgumentException("Quiz does not support Answers")

            return GameQuizDetail(
                id = quiz.id.value,
                content = quiz.content.content,
                options = quiz.content.options.map { GameQuizOption(it.optionNumber, it.content) },
                answer = findAnswer(quiz.content.options, quiz)
            )
        }

        private fun findAnswer(options: List<QuizOption>, quiz: Answerable): GameQuizAnswer {
            val answerOption = options.first { it.optionNumber == quiz.getCorrectAnswer() }
            return GameQuizAnswer(answerOption.content, quiz.getAnswerExplanation())
        }
    }
}

data class GameQuizOption(val no: Int, val content: String)

data class GameQuizAnswer(val content: String, val explanation: String)