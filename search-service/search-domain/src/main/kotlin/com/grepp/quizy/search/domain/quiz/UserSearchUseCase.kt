package com.grepp.quizy.search.domain.quiz

import com.grepp.quizy.search.domain.global.dto.Slice
import com.grepp.quizy.search.domain.user.UserId

interface UserSearchUseCase {
    fun searchByKeyword(
        userId: UserId?,
        condition: UserSearchCondition,
    ): Slice<QuizWithDetail>
}
