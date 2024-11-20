package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.user.UserId
import org.springframework.stereotype.Component

@Component
class QuizMetadataCombiner(
    private val quizSearcher: QuizSearcher,
    private val quizFetcher: QuizFetcher
) {

    fun combine(userId: UserId?, searchedQuizzes: Slice<Quiz>): List<SearchedQuiz> {
        val quizIds = parseQuizIds(searchedQuizzes.content)
        val likeStatus = quizFetcher.fetchUserLikeStatus(quizIds)

        val answerableQuiz = filterAnswerableQuiz(searchedQuizzes)
        val userAnswer = userId?.let { id -> getUserAnswer(id, answerableQuiz) } ?: UserAnswer()

        return searchedQuizzes.content.map { quiz ->
            QuizDTOFactory.SearchedQuiz(quiz, likeStatus.isLikedBy(quiz.id), userAnswer.getAnswerOf(quiz.id))
        }
    }

    private fun getUserAnswer(userId: UserId, answerableQuiz: List<Quiz>) =
        quizSearcher.searchUserAnswer(userId, parseQuizIds(answerableQuiz))

    private fun parseQuizIds(quizzes: List<Quiz>) = quizzes.map { it.id }

    private fun filterAnswerableQuiz(quizSlice: Slice<Quiz>) = quizSlice.content.filter { it is Answerable }
}