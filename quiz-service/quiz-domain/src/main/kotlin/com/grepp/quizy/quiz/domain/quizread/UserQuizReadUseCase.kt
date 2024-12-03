package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.user.UserId

interface UserQuizReadUseCase {
    fun searchRecommendedFeed(userId: UserId?, condition: FeedSearchCondition): QuizFeed
}