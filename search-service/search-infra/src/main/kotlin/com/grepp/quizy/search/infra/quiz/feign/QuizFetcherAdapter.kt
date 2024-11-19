package com.grepp.quizy.search.infra.quiz.feign

import com.grepp.quizy.search.domain.quiz.*
import org.springframework.stereotype.Component

@Component
class QuizFetcherAdapter : QuizFetcher {
    // @TODO 퀴즈 서비스에 API 요청해서 좋아요, 댓글 수 조회 필요
    override fun fetchQuizCount(quizIds: List<QuizId>) = QuizCountPackage(quizIds.associateWith { QuizCount(0, 0) })

    // @TODO 퀴즈 서비스에 API 요청해서 좋아요 여부 조회 필요
    override fun fetchUserLikeStatus(quizIds: List<QuizId>) = QuizLikeStatus(quizIds.associateWith { true })
}