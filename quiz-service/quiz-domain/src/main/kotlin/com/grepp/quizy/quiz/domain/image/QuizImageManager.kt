package com.grepp.quizy.quiz.domain.image

import com.grepp.quizy.quiz.domain.image.exception.QuizImageDomainException
import org.springframework.stereotype.Component

@Component
class QuizImageManager(
    private val quizImageRepository: QuizImageRepository
) {
    fun append(image: QuizImage): QuizImage {
        return quizImageRepository.save(image)
    }

    fun read(id: QuizImageId): QuizImage {
        return quizImageRepository.findById(id) ?: throw QuizImageDomainException.NotFound
    }

    fun readIn(ids: List<QuizImageId>): List<QuizImage> =
        quizImageRepository.findAllById(ids)

    fun delete(id: Long) {
        quizImageRepository.deleteById(id)
    fun remove(ids: List<QuizImageId>) {
        quizImageRepository.deleteByIdIn(ids)
    }
}