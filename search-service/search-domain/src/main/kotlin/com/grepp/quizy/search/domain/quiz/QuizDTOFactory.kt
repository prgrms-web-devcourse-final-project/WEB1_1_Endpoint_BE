package com.grepp.quizy.search.domain.quiz

class QuizDTOFactory {
    companion object {
        fun SearchedQuiz(
                quiz: Quiz,
                isLiked: Boolean,
                answeredOption: OptionNumber?,
        ): SearchedQuiz =
                when (quiz) {
                    is ABTest -> NonAnswerableQuiz.from(quiz, isLiked)
                    is MultipleOptionQuiz ->
                            AnswerableQuiz(
                                    quiz,
                                    isLiked,
                                    answeredOption,
                            )
                    is OXQuiz ->
                            AnswerableQuiz(
                                    quiz,
                                    isLiked,
                                    answeredOption,
                            )
                }

        private fun <T> AnswerableQuiz(
                quiz: T,
                isLiked: Boolean,
                answeredOption: OptionNumber?,
        ): AnswerableQuiz where T : Quiz, T : Answerable =
                answeredOption?.let {
                    UserAnsweredQuiz.from(
                            quiz,
                            answeredOption.value,
                            isLiked,
                    )
                } ?: UserNotAnsweredQuiz.from(quiz, isLiked)
    }
}
