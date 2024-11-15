package com.grepp.quizy.common.domain

abstract class BaseId<T>(open val value: T) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseId<*>) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}
