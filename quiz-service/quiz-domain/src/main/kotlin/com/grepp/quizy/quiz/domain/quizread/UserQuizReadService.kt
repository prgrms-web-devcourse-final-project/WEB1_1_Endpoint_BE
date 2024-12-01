package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.user.QuizUserReader
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class UserQuizReadService(
    private val quizUserReader: QuizUserReader,
    private val quizSearcher: QuizSearcher,
    private val quizMetadataCombiner: QuizMetadataCombiner
) : UserQuizReadUseCase {

    override fun searchRecommendedFeed(userId: UserId, condition: FeedSearchCondition): Slice<QuizWithDetail> {
        val searchedQuizzes = condition.interest?.let {
            quizSearcher.searchByCategory(userId, condition)
        } ?: quizSearcher.searchByCategory(userId, FeedSearchCondition(condition.page, findUserInterest(userId)))
        val content = quizMetadataCombiner.combineWithoutUserAnswer(userId, searchedQuizzes)
        return Slice(content, searchedQuizzes.hasNext)
    }

    private fun findUserInterest(userId: UserId) =
        quizUserReader.read(userId).interests.random()
}