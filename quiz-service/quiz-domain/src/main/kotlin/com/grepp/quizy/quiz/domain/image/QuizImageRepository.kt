package com.grepp.quizy.quiz.domain.image

interface QuizImageRepository {

    fun save(image: QuizImage): QuizImage

    fun findById(id: QuizImageId): QuizImage?

    fun deleteById(id: Long)

}
