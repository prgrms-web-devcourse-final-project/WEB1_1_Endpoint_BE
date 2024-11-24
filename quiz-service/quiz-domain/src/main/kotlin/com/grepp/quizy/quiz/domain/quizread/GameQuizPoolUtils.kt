package com.grepp.quizy.quiz.domain.quizread

class GameQuizPoolUtils {
    companion object {
        private const val QUIZ_POOL_SIZE_MULTIPLE = 10

        fun expandQuizPoolSize(original: Int) = original * QUIZ_POOL_SIZE_MULTIPLE

        fun makeGameSet(candidates: List<AnswerableQuiz>, targetSize: Int) =
            candidates.shuffled().take(targetSize).map { GameQuizDetail.from(it) }
    }
}