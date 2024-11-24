package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.useranswer.Choice

class QuizDTOFactory {
    companion object {
        fun QuizWithDetail(
            quiz: QuizForRead,
            isLiked: Boolean,
            answered: Choice?,
        ): QuizWithDetail =
                when (quiz) {
                    is ABTest -> NotAnswerableQuizDetail(quiz, isLiked, answered)
                    is AnswerableQuiz -> AnswerableQuizDetail(quiz, isLiked, answered,)
                }

        private fun NotAnswerableQuizDetail(
            quiz: QuizForRead,
            isLiked: Boolean,
            answered: Choice?,
        ): NotAnswerableQuizDetail =
            answered?.let {
                AnsweredQuizWithoutAnswer.from(
                    quiz,
                    answered.value,
                    isLiked,
                )
            } ?: NotAnsweredQuizWithoutAnswer.from(quiz, isLiked)

        private fun AnswerableQuizDetail(
            quiz: AnswerableQuiz,
            isLiked: Boolean,
            answered: Choice?,
        ): AnswerableQuizDetail =
                answered?.let {
                    AnsweredQuizWithAnswer.from(
                            quiz,
                            answered.value,
                            isLiked,
                    )
                } ?: NotAnsweredQuizWithAnswer.from(quiz, isLiked)
    }
}
