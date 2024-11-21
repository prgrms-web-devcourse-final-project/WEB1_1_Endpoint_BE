package com.grepp.quizy.search.infra.quiz.feign

import com.grepp.quizy.search.domain.quiz.*
import org.springframework.stereotype.Component

@Component
class QuizFetcherAdapter : QuizFetcher {

    // @TODO 퀴즈 서비스에 API 요청해서 좋아요 여부 조회 필요
    override fun fetchUserLikeStatus(quizIds: List<QuizId>) =
            QuizLikeStatus(quizIds.associateWith { true })
}
