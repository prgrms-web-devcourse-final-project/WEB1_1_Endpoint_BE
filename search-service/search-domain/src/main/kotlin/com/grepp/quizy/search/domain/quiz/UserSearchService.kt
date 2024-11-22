package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class UserSearchService(
        private val quizSearcher: QuizSearcher,
        private val quizMetadataCombiner: QuizMetadataCombiner,
) : UserSearchUseCase {

    override fun searchByKeyword(
        userId: UserId?,
        condition: UserSearchCondition,
    ): Slice<QuizWithDetail> {
        val searchedQuizzes = quizSearcher.search(condition)
        val content =
                quizMetadataCombiner.combine(userId, searchedQuizzes)
        return Slice(content, searchedQuizzes.hasNext)
    }
}
