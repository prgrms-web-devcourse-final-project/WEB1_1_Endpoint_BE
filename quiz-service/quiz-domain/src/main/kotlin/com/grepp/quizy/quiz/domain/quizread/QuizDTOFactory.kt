package com.grepp.quizy.quiz.domain.quizread

class QuizDTOFactory {
    companion object {
        fun QuizWithDetail(
            quiz: QuizForRead,
            isLiked: Boolean,
            answeredOption: OptionNumber?,
        ): QuizWithDetail =
                when (quiz) {
                    is ABTest -> NonAnswerableQuizWithDetail.from(quiz, isLiked)
                    is AnswerableQuiz ->
                            AnswerableQuizDetail(
                                    quiz,
                                    isLiked,
                                    answeredOption,
                            )
                }

        private fun AnswerableQuizDetail(
                quiz: AnswerableQuiz,
                isLiked: Boolean,
                answeredOption: OptionNumber?,
        ): AnswerableQuizDetail =
                answeredOption?.let {
                    UserAnsweredQuiz.from(
                            quiz,
                            answeredOption.value,
                            isLiked,
                    )
                } ?: UserNotAnsweredQuiz.from(quiz, isLiked)
    }
}
