package com.grepp.quizy.quiz.api.image

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.domain.image.QuizImage
import com.grepp.quizy.quiz.domain.image.QuizImageId
import com.grepp.quizy.quiz.domain.image.QuizImageService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/quiz/images")
class QuizImageApi(
    private val quizImageService: QuizImageService
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(@RequestPart file: MultipartFile): ApiResponse<QuizImage> =
        ApiResponse.success(quizImageService.uploadImage(toImageFile(file)))


    @GetMapping("/{id}")
    fun getImage(id: Long): ApiResponse<QuizImage> =
        ApiResponse.success(quizImageService.getImage(QuizImageId.from(id)))
}