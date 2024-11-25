package com.grepp.quizy.quiz.domain.like

import com.grepp.quizy.quiz.domain.quiz.QuizCache
import com.grepp.quizy.quiz.domain.quiz.QuizId
import com.grepp.quizy.quiz.domain.useranswer.UserId
import org.springframework.stereotype.Component

@Component
class LikeManager(
        private val likeRepository: LikeRepository,
        private val quizCache: QuizCache,
) {

    fun toggleLike(like: Like): Boolean {
        return if (!isLiked(like)) {
            quizCache.cacheLike(like)
            quizCache.increaseLikeCount(like.quizId)
            likeRepository.save(like)
            true
        } else {
            quizCache.deleteLike(like)
            quizCache.decreaseLikeCount(like.quizId)
            likeRepository.delete(like)
            false
        }
    }

    fun isLikedIn(
            userId: UserId,
            quizIds: List<QuizId>,
    ) = QuizLikePackage(quizIds.associateWith { isLiked(Like(userId, it)) })

    private fun isLiked(like: Like): Boolean =
            quizCache.isLikeCached(like)
                    ?: likeRepository.exists(like).also { exists ->
                        if (exists) quizCache.cacheLike(like)
                    }
}
