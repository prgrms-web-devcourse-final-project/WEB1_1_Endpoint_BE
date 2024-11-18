package com.grepp.quizy.search.domain.quiz

class QuizDTOFactory {
    companion object {
        fun SearchedQuiz(quiz: Quiz, extras: SearchedQuizExtras, answeredOption: OptionNumber?): SearchedQuiz =
            when (quiz) {
                is ABTest -> NonAnswerableQuiz.from(quiz, extras)
                is MultipleOptionQuiz -> AnswerableQuiz(quiz, extras, answeredOption)
                is OXQuiz -> AnswerableQuiz(quiz, extras, answeredOption)
            }

        private fun <T> AnswerableQuiz(
            quiz: T,
            extras: SearchedQuizExtras,
            answeredOption: OptionNumber?
        ): AnswerableQuiz where T : Quiz, T : Answerable =
            answeredOption?.let {
                UserAnsweredQuiz.from(quiz, answeredOption.value, extras)
            } ?: UserNotAnsweredQuiz.from(quiz, extras)
    }
}