package com.grepp.quizy.quiz.domain.image

interface QuizImageRepository {

    fun save(image: QuizImage): QuizImage

    fun findById(id: QuizImageId): QuizImage?

    fun deleteByIdIn(ids: List<QuizImageId>)

    fun findAllById(ids: List<QuizImageId>): List<QuizImage>
}
