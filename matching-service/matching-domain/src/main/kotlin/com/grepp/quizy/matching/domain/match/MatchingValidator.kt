package com.grepp.quizy.matching.domain.match

import org.springframework.stereotype.Component

@Component
class MatchingValidator(
    private val matchingQueueRepository: MatchingQueueRepository
) {
    fun isPivotInvalid(pivot: UserStatus) = !matchingQueueRepository.isValid(pivot.userId)

    fun isCandidatesInvalid(pivot: UserStatus, candidates: List<UserStatus>): Boolean {
        val final = mutableListOf<UserStatus>()
        candidates.forEach {
            if (matchingQueueRepository.isValid(it.userId)) final.add(it)
        }

        if (final.size < candidates.size || candidates.size < 2) {
            matchingQueueRepository.enqueue(pivot)
            return true
        }
        return false
    }
}