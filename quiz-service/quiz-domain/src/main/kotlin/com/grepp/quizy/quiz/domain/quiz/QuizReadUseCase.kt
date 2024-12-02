package com.grepp.quizy.quiz.domain.quiz

import com.grepp.quizy.common.dto.Cursor
import com.grepp.quizy.common.dto.SliceResult
import com.grepp.quizy.quiz.domain.user.UserId

interface QuizReadUseCase {

    fun getQuizTags(ids: List<QuizTagId>): List<QuizTag>
    fun getQuiz(quizId: QuizId): Quiz
    fun getMyQuizzes(creatorId: UserId, cursor: Cursor): SliceResult<Quiz>
}
