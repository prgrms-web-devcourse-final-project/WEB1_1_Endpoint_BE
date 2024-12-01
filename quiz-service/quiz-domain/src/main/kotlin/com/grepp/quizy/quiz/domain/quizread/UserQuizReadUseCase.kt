package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.common.dto.Page
import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.user.UserId

interface UserQuizReadUseCase {
    fun searchRecommendedFeed(userId: UserId, page: Page): Slice<QuizWithDetail>
}