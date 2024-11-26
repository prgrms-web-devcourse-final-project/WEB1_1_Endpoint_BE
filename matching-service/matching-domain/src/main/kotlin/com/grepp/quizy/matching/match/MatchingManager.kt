package com.grepp.quizy.matching.match

import com.grepp.quizy.matching.game.GameFetcher
import com.grepp.quizy.matching.user.InterestCategory
import org.springframework.stereotype.Component

@Component
class MatchingManager(
    private val gameFetcher: GameFetcher,
    private val matchingEventPublisher: MatchingEventPublisher,
    private val matchingPoolRepository: MatchingPoolRepository,
    private val matchingQueueRepository: MatchingQueueRepository
) {

    fun match(pivot: UserStatus) {
        val candidates = matchingPoolRepository.findNearestUser(pivot)
        if (!validateCandidates(pivot, candidates)) return

        removeFromPool(candidates)
        val gameRoodId = gameFetcher.requestGameRoomId(
                candidates.map { it.userId },
                parseCommonInterestCategory(candidates.map { it.vector.value })
            )
        candidates.forEach {
            matchingEventPublisher.publish(PersonalMatchingSucceedEvent(it.userId.value, gameRoodId.value, candidates.map {it.userId.value}))
        }
    }

    private fun validateCandidates(pivot: UserStatus, candidates: List<UserStatus>): Boolean {
        val final = mutableListOf<UserStatus>()
        candidates.forEach {
            if (matchingQueueRepository.isValid(it.userId)) final.add(it)
        }

        if (final.size < candidates.size || candidates.size < MATCHING_K) {
            matchingQueueRepository.enqueue(pivot)
            return false
        }
        return true
    }

    private fun parseCommonInterestCategory(vectors: List<FloatArray>): InterestCategory {
        val size = vectors.first().size
        val common = (1 until size).filter { index ->
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