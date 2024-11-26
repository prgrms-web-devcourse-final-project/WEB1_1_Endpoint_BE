package com.grepp.quizy.quiz.domain.image

interface QuizImageUploader {
    fun upload(imageFile: ImageFile): QuizImage
}