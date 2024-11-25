package com.grepp.quizy.quiz.domain.like

interface LikeRepository {
    fun save(like: Like)

    fun delete(like: Like)

    fun exists(like: Like): Boolean
}
