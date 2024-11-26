package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.domain.useranswer.Choice

class QuizDTOFactory {
    companion object {
        fun QuizWithDetail(
            quiz: Quiz,
            count: QuizCount,
            isLiked: Boolean,
            answered: Choice?,
        ): QuizWithDetail =
                when (quiz) {
                    is ABTest -> NotAnswerableQuizDetail(quiz, count, isLiked, answered)
                    is OXQuiz -> AnswerableQuizDetail(quiz, count, isLiked, answered)
                    is MultipleChoiceQuiz -> AnswerableQuizDetail(quiz, count, isLiked, answered)
                }

        private fun NotAnswerableQuizDetail(
            quiz: Quiz,
            count: QuizCount,
            isLiked: Boolean,
            answered: Choice?,
        ): NotAnswerableQuizDetail =
            answered?.let {
                AnsweredQuizWithoutAnswer.from(
                    quiz,
                    count,
                    answered.value,
                    isLiked,
                )
            } ?: NotAnsweredQuizWithoutAnswer.from(quiz, count, isLiked)

        private fun <T> AnswerableQuizDetail(
            quiz: T,
            count: QuizCount,
            isLiked: Boolean,
            answered: Choice?,
        ): AnswerableQuizDetail where T : Quiz, T : Answerable =
                answered?.let {
                    AnsweredQuizWithAnswer.from(
                        quiz,
                        count,
                        answered.value,
                        isLiked,
                    )
                } ?: NotAnsweredQuizWithAnswer.from(quiz, count, isLiked)
    }
}
