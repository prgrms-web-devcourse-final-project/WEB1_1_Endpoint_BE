package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.user.UserId

interface UserQuizSearchUseCase {
    fun searchByKeyword(
        userId: UserId?,
        condition: UserSearchCondition,
    ): Slice<QuizWithDetail>
}
