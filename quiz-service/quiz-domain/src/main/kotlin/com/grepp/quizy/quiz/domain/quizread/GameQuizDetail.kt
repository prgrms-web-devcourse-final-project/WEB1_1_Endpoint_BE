package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.quiz.Answerable
import com.grepp.quizy.quiz.domain.quiz.Quiz

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
                answer = GameQuizAnswer(quiz.getCorrectAnswer(), quiz.getAnswerExplanation())
            )
        }
    }
}

data class GameQuizOption(val no: Int, val content: String)

data class GameQuizAnswer(val content: String, val explanation: String)