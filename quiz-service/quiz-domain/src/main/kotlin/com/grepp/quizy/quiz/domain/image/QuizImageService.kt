package com.grepp.quizy.quiz.domain.image

import org.springframework.stereotype.Service

@Service
class QuizImageService(
    private val quizImageUploader: QuizImageUploader,
    private val quizImageAppender: QuizImageManager
) {

    fun uploadImage(imageFile: ImageFile): QuizImage {
        val quizImage = quizImageUploader.upload(imageFile)
        return quizImageAppender.append(quizImage)
    }
}