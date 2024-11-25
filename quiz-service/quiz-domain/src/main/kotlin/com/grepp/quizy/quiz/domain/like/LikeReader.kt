package com.grepp.quizy.quiz.domain.like

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.stereotype.Component

@Component
class LikeReader(private val repository: LikeRepository) {
    fun isLikedBy(userId: UserId, quizIds: List<QuizId>): QuizLikePackage {
        TODO("퀴즈별 사용자의 좋아요 여부 조회")
    }
}