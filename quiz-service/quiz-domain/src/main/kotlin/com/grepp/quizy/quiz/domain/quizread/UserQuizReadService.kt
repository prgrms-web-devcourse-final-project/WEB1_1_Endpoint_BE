package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.quiz.QuizCategory
import com.grepp.quizy.quiz.domain.user.QuizUserReader
import com.grepp.quizy.quiz.domain.user.UserId
import org.springframework.stereotype.Service


@Service
class UserQuizReadService(
    private val quizUserReader: QuizUserReader,
    private val quizSearcher: QuizSearcher,
    private val quizMetadataCombiner: QuizMetadataCombiner
) : UserQuizReadUseCase {

    override fun searchRecommendedFeed(userId: UserId?, condition: FeedSearchCondition): QuizFeed {
        return userId?.let { id ->
            searchUserPersonalFeed(id, condition)
        } ?: searchRandomFeed(condition)
    }

    private fun searchUserPersonalFeed(userId: UserId, condition: FeedSearchCondition): QuizFeed {
        val feedCondition = condition.interest?.let { condition }
            ?: FeedSearchCondition(condition.page, findUserInterest(userId))
        val searchedQuizzes = quizSearcher.searchByCategoryUnanswered(userId, feedCondition)
        val content = quizMetadataCombiner.combineWithoutUserAnswer(userId, searchedQuizzes)

        return QuizFeed(feedCondition.interest!!, Slice(content, searchedQuizzes.hasNext))
    }

    private fun searchRandomFeed(condition: FeedSearchCondition): QuizFeed {
        val feedCondition = condition.interest?.let { condition }
            ?: FeedSearchCondition(condition.page, QuizCategory.entries.random())
        val searchedQuizzes = quizSearcher.searchByCategory(feedCondition)
        val content = quizMetadataCombiner.combine(null, searchedQuizzes)

        return QuizFeed(feedCondition.interest!!, Slice(content, searchedQuizzes.hasNext))
    }

    private fun findUserInterest(userId: UserId) = quizUserReader.read(userId).interests.random()
}