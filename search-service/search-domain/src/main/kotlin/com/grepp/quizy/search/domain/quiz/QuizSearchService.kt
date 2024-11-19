package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class QuizSearchService(
    private val quizSearcher: QuizSearcher,
    private val quizFetcher: QuizFetcher
) : QuizSearchUseCase {

    override fun searchByKeyword(userId: UserId?, condition: SearchCondition): Slice<SearchedQuiz> {
        val searchedQuizzes = quizSearcher.search(condition)
        val quizIds = searchedQuizzes.content.map { it.id }
        val quizAdditional = quizFetcher.getQuizAdditionalData(quizIds)

        val answerableQuiz = searchedQuizzes.content.filter { it is AnswerableQuiz }
        val userAnswer = userId?.let { id ->
            quizSearcher.searchUserAnswer(id, answerableQuiz.map { it.id })
        } ?: UserAnswer()

        val content = searchedQuizzes.content.map { quiz ->
            QuizDTOFactory.SearchedQuiz(quiz, quizAdditional.get(quiz.id), userAnswer.getAnswerOf(quiz.id))
        }

        return Slice(content, searchedQuizzes.hasNext)
    }
}