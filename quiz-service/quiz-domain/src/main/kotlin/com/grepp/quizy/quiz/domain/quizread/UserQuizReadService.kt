package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.common.dto.Page
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

    override fun searchRecommendedFeed(userId: UserId, page: Page): Slice<QuizWithDetail> {
        val interests = quizUserReader.read(userId).interests
        val searchedQuizzes = quizSearcher.searchByCategory(userId, FeedSearchCondition(page, interests.random()))
        val content = quizMetadataCombiner.combineWithoutUserAnswer(userId, searchedQuizzes)
        return Slice(content, searchedQuizzes.hasNext)
    }
}