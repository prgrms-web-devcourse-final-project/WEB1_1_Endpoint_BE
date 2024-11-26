package com.grepp.quizy.quiz.domain.quizread

import com.grepp.quizy.quiz.domain.global.dto.Slice
import com.grepp.quizy.quiz.domain.user.UserId

interface UserQuizSearchUseCase {
    fun searchByKeyword(
        userId: UserId?,
        condition: UserSearchCondition,
    ): Slice<QuizWithDetail>

    fun searchUnansweredByKeyword(userId: UserId, condition: UserSearchCondition): Slice<QuizWithDetail>
}
