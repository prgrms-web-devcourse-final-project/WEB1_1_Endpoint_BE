package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizMetadataCombiner(
        private val quizSearcher: QuizSearcher,
        private val quizFetcher: QuizFetcher,
) {

    fun combine(
        userId: UserId?,
        searchedQuizzes: Slice<QuizForRead>,
    ): List<QuizWithDetail> {
        val quizIds = parseQuizIds(searchedQuizzes.content)
        val likeStatus = quizFetcher.fetchUserLikeStatus(quizIds)

        val answerableQuiz = filterAnswerableQuiz(searchedQuizzes)
        val userAnswer =
                userId?.let { id ->
                    getUserAnswer(id, answerableQuiz)
                } ?: UserAnswer()

        return searchedQuizzes.content.map { quiz ->
            QuizDTOFactory.QuizWithDetail(
                    quiz,
                    likeStatus.isLikedBy(quiz.id),
                    userAnswer.getAnswerOf(quiz.id),
            )
        }
    }

    private fun getUserAnswer(
        userId: UserId,
        answerableQuiz: List<QuizForRead>,
    ) =
            quizSearcher.searchUserAnswer(
                    userId,
                    parseQuizIds(answerableQuiz),
            )

    private fun parseQuizIds(quizzes: List<QuizForRead>) =
            quizzes.map { it.id }

    private fun filterAnswerableQuiz(quizSlice: Slice<QuizForRead>) =
            quizSlice.content.filter { it is Answerable }
}
