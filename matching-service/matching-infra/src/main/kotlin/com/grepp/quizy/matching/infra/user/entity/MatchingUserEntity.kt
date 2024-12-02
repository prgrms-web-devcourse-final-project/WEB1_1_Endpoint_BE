package com.grepp.quizy.matching.infra.user.entity

import com.grepp.quizy.matching.domain.user.MatchingUser
import com.grepp.quizy.matching.domain.user.UserId
import jakarta.persistence.*

@Entity
@Table(name = "users")
class MatchingUserEntity(
    @Id val id: Long = 0,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_interests",
        joinColumns = [JoinColumn(name = "user_id")]
    )
    val interests: MutableList<MatchingUserInterestVO> = mutableListOf()
) {

    fun MatchingUser() = MatchingUser(UserId(id), interests.map { it.interest })
}