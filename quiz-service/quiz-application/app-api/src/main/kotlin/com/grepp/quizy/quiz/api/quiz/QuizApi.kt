package com.grepp.quizy.quiz.api.quiz

import com.grepp.quizy.common.api.ApiResponse
import com.grepp.quizy.quiz.domain.Quiz
import com.grepp.quizy.quiz.domain.QuizService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quiz")
class QuizApi(private val quizService: QuizService) {

    @PostMapping
    fun createQuiz(
            @RequestBody request: CreateQuizRequest
    ): ApiResponse<Quiz> =
            ApiResponse.success(
                    quizService.createQuiz(
                            request.toContent(),
                            request.toQuizTags(),
                            request.options,
                            request.toAnswer(),
                    )
            )
}
