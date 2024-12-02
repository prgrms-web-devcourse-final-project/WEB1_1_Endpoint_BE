package com.grepp.quizy.quiz.domain.useranswer

import com.grepp.quizy.common.dto.Cursor
import com.grepp.quizy.common.dto.SliceResult
import com.grepp.quizy.quiz.domain.user.UserId

interface UserAnswerReadService {

    fun getIncorrectQuizzes(userId: UserId, cursor: Cursor): SliceResult<QuizWithUserAnswer>

}
