package com.grepp.quizy.quiz.domain.like

import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.stereotype.Component

@Component
class LikeReader(private val repository: LikeRepository) {
    fun isLikedBy(likerId: UserId, quizIds: List<QuizId>) =
        repository.existsAll(quizIds.map { Like(likerId, it) })
}