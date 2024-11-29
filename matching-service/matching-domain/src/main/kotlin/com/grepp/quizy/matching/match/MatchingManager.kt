package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.game.GameFetcher
import com.grepp.quizy.matching.user.InterestCategory
import org.springframework.stereotype.Component

@Component
class MatchingManager(
    private val gameFetcher: GameFetcher,
    private val matchingEventPublisher: MatchingEventPublisher,
    private val matchingValidator: MatchingValidator,
    private val matchingPoolRepository: MatchingPoolRepository,
    private val matchingQueueRepository: MatchingQueueRepository
) {

    fun match(pivot: UserStatus) {
        val candidates = matchingPoolRepository.findNearestUser(pivot)
        if (matchingValidator.isCandidatesInvalid(pivot, candidates)) return

        removeFromPool(candidates)
        val gameRoodId = gameFetcher.requestGameRoomId(
                candidates.map { it.userId },
                parseCommonInterestCategory(candidates.map { it.vector.value })
            )
        candidates.forEach { candidate ->
            matchingEventPublisher.publish(
                PersonalMatchingSucceedEvent(
                    candidate.userId.value,
                    gameRoodId.value,
                    candidates.map {it.userId.value}
                )
            )
        }
    }

    private fun parseCommonInterestCategory(vectors: List<FloatArray>): InterestCategory {
        val size = vectors.first().size
        val common = (InterestCategory.VECTOR_START_INDEX until size).filter { index ->
            vectors.all { array -> array[index] == 1f }
        }
        return InterestCategory.commonInterest(common)
    }

    private fun removeFromPool(candidates: List<UserStatus>) {
        candidates.forEach {
            matchingPoolRepository.remove(it.userId)
            matchingQueueRepository.removeSet(it.userId)
        }
    }
}