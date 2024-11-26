package com.grepp.quizy.quiz.domain.image

interface QuizImageRepository {

    fun save(image: QuizImage): QuizImage

    fun findById(id: Long): QuizImage?

    fun deleteById(id: Long)

}
