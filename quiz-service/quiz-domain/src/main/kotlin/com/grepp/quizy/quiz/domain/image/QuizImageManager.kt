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

    fun read(id: Long): QuizImage {
        return quizImageRepository.findById(id) ?: throw QuizImageDomainException.NotFound
    }

    fun delete(id: Long) {
        quizImageRepository.deleteById(id)
    }
}