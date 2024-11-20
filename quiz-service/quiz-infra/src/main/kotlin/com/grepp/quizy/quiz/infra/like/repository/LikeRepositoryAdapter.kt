package com.grepp.quizy.quiz.infra.like.repository

import com.grepp.quizy.quiz.domain.like.Like
import com.grepp.quizy.quiz.domain.like.LikeRepository
import com.grepp.quizy.quiz.infra.like.entity.LikeEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class LikeRepositoryAdapter(
        private val likeJpaRepository: LikeJpaRepository
) : LikeRepository {

    override fun save(like: Like) {
        likeJpaRepository.save(LikeEntity.from(like))
    }

    override fun delete(like: Like) {
        likeJpaRepository.delete(LikeEntity.from(like))
    }

    override fun exists(like: Like): Boolean {
        return likeJpaRepository.existsById(LikeEntity.from(like).id)
    }
}
