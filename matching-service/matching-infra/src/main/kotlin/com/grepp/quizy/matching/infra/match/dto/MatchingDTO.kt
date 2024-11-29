package com.grepp.quizy.matching.infra.match.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.grepp.quizy.matching.domain.match.UserStatus
import com.grepp.quizy.matching.infra.match.converter.FloatArrayDeserializer
import com.grepp.quizy.matching.domain.user.UserId
import com.grepp.quizy.matching.domain.user.UserVector

data class RedisUserStatus @JsonCreator constructor(
    @JsonProperty("userId") val userId: Long,
    @JsonProperty("vector") @JsonDeserialize(using = FloatArrayDeserializer::class) val vector: FloatArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RedisUserStatus

        if (userId != other.userId) return false
        if (!vector.contentEquals(other.vector)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + vector.contentHashCode()
        return result
    }

    fun UserStatus() = UserStatus(UserId(userId), UserVector(vector))

    companion object {
        fun from(userStatus: UserStatus) = RedisUserStatus(userStatus.userId.value, userStatus.vector.value)
    }
}