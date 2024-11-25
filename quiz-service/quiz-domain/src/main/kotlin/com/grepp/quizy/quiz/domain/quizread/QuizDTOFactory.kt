package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.quiz.QuizCount
import com.grepp.quizy.quiz.domain.useranswer.Choice

class QuizDTOFactory {
    companion object {
        fun QuizWithDetail(
            quiz: QuizForRead,
            count: QuizCount,
            isLiked: Boolean,
            answered: Choice?,
        ): QuizWithDetail =
                when (quiz) {
                    is ABTest -> NotAnswerableQuizDetail(quiz, count, isLiked, answered)
                    is AnswerableQuiz -> AnswerableQuizDetail(quiz, count, isLiked, answered,)
                }

        private fun NotAnswerableQuizDetail(
            quiz: QuizForRead,
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

        private fun AnswerableQuizDetail(
            quiz: AnswerableQuiz,
            count: QuizCount,
            isLiked: Boolean,
            answered: Choice?,
        ): AnswerableQuizDetail =
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
