package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.quiz.*
import com.grepp.quizy.quiz.domain.useranswer.Choice

class QuizDTOFactory {
    companion object {
        fun QuizWithDetail(
            quiz: Quiz,
            author: QuizAuthor?,
            count: QuizCount,
            isLiked: Boolean,
            answered: Choice?,
        ): QuizWithDetail =
                when (quiz) {
                    is ABTest -> NotAnswerableQuizDetail(quiz, author, count, isLiked, answered)
                    is OXQuiz -> AnswerableQuizDetail(quiz, author, count, isLiked, answered)
                    is MultipleChoiceQuiz -> AnswerableQuizDetail(quiz, author, count, isLiked, answered)
                }

        private fun NotAnswerableQuizDetail(
            quiz: Quiz,
            author: QuizAuthor?,
            count: QuizCount,
            isLiked: Boolean,
            answered: Choice?,
        ): NotAnswerableQuizDetail =
            answered?.let {
                AnsweredQuizWithoutAnswer.from(
                    quiz,
                    author,
                    count,
                    answered.value,
                    isLiked,
                )
            } ?: NotAnsweredQuizWithoutAnswer.from(quiz, author, count, isLiked)

        private fun <T> AnswerableQuizDetail(
            quiz: T,
            author: QuizAuthor?,
            count: QuizCount,
            isLiked: Boolean,
            answered: Choice?,
        ): AnswerableQuizDetail where T : Quiz, T : Answerable =
                answered?.let {
                    AnsweredQuizWithAnswer.from(
                        quiz,
                        author,
                        count,
                        answered.value,
                        isLiked,
                    )
                } ?: NotAnsweredQuizWithAnswer.from(quiz, author, count, isLiked)
    }
}
